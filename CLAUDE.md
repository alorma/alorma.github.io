# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a GitHub Pages Repository Aggregator built with Kotlin Multiplatform Compose targeting WebAssembly (WasmJS). The application:

1. Queries GitHub repositories for user `alorma`
2. Filters repositories that have GitHub Pages enabled
3. Displays repositories with websites hosted on `alorma.github.io` domain
4. Provides clickable links to easily access these GitHub Pages sites

The app serves as a dashboard to quickly navigate between all GitHub Pages projects.

## Build Commands

```bash
# Build the project
./gradlew build

# Assemble the project (without tests)
./gradlew assemble

# Clean build artifacts
./gradlew clean

# Run all tests
./gradlew check

# Run WASM browser tests specifically
./gradlew composeApp:wasmJsBrowserTest

# Compile Kotlin to WebAssembly
./gradlew composeApp:compileKotlinWasmJs

# Create browser distribution with JS fallback
./gradlew composeApp:composeCompatibilityBrowserDistribution
```

## Architecture

### Clean Architecture with MVVM

The project follows Clean Architecture principles with clear separation of concerns:

- **Domain Layer** - Business logic and use cases (platform-agnostic)
- **Data Layer** - Data sources and repositories
- **Presentation Layer** - ViewModels and UI state management
- **UI Layer** - Compose UI components

### Project Structure

```
composeApp/src/
├── commonMain/kotlin/com/alorma/playground/
│   ├── App.kt - Main app entry point with Koin setup
│   ├── domain/
│   │   ├── model/Repository.kt - Domain model
│   │   ├── datasource/RepositoryDataSource.kt - Data source interface
│   │   ├── usecase/GetGitHubPagesRepositoriesUseCase.kt - Business logic
│   │   └── di/DomainModule.kt - Koin DI for domain layer
│   ├── data/
│   │   ├── datasource/FakeRepositoryDataSource.kt - Fake implementation
│   │   └── di/DataModule.kt - Koin DI for data layer
│   ├── ui/
│   │   ├── RepositoriesScreen.kt - Main screen composable
│   │   ├── RepositoriesViewModel.kt - State management
│   │   ├── RepositoryListItem.kt - List item composable
│   │   └── di/UiModule.kt - Koin DI for UI layer
│   ├── navigation/
│   │   └── NavigationDelegate.kt - Platform-agnostic navigation interface
│   ├── platform/
│   │   ├── Platform.kt - expect/actual platform utilities
│   │   └── di/PlatformModule.kt - expect declaration for platform DI
│   └── di/
│       └── AppModule.kt - Root Koin module aggregator
└── wasmJsMain/
    ├── kotlin/com/alorma/playground/
    │   ├── main.kt - WASM entry point with URL parsing
    │   ├── navigation/
    │   │   └── WasmNavigationDelegate.kt - WASM navigation implementation
    │   ├── platform/
    │   │   ├── Platform.kt - actual WASM platform implementation
    │   │   └── di/PlatformModule.wasmJs.kt - actual WASM DI module
    └── resources/
        ├── index.html - HTML entry point
        └── styles.css - CSS styles
```

### Build System

- Uses Gradle with Kotlin DSL (`.gradle.kts`)
- Version catalog in `gradle/libs.versions.toml` defines all dependencies
- Targets WASM using `@OptIn(ExperimentalWasmDsl::class)`
- Compose compiler plugin is applied for Compose Multiplatform support

### Key Technologies

- **Kotlin 2.3.0** - Language version
- **Compose Multiplatform 1.9.3** - UI framework
- **Compose Compiler** - Kotlin compiler plugin for Compose
- **AndroidX Lifecycle 2.9.6** - ViewModel and runtime Compose integration
- **Koin 4.0.0** - Dependency injection for Kotlin Multiplatform
- **Kotlin Coroutines & Flow** - Asynchronous programming and reactive streams
- **WASM target** - Experimental WebAssembly compilation

### Source Sets

The project uses Kotlin Multiplatform source sets:
- `commonMain` - Shared code across all targets
- `wasmJsMain` - WASM-specific implementations (platform code)
- `commonTest` - Shared test code

### Dependencies

All dependencies are defined in `gradle/libs.versions.toml` and referenced using type-safe accessors like `libs.plugins.kotlinMultiplatform`. Main dependencies include:
- Compose runtime, foundation, Material3, and UI
- Compose resources and UI tooling preview
- Lifecycle ViewModel and Runtime Compose
- Koin Core and Koin Compose for dependency injection

## Application Features

### Dynamic Username Detection

The app automatically detects the GitHub username from the URL:
- **Hostname parsing**: Extracts username from `username.github.io` subdomain pattern
- **Localhost support**: Defaults to `"alorma"` when running on localhost/127.0.0.1
- **No user fallback**: Shows error screen if URL doesn't match expected pattern

### Repository Display

- **Filtering Logic**: Displays only repositories with GitHub Pages URLs matching `username.github.io` pattern
- **UI**: Card-based list showing repository name, description, and Pages URL
- **Navigation**: Clicking a card opens the GitHub Pages site in a new browser tab

### Architecture Implementation

#### Domain Layer
- **Repository** - Domain model with name, fullName, htmlUrl, pagesUrl, description
- **RepositoryDataSource** - Interface for data fetching
- **GetGitHubPagesRepositoriesUseCase** - Filters repositories by GitHub Pages URL pattern

#### Data Layer
- **FakeRepositoryDataSource** - Mock implementation (Ktor implementation pending)
- Returns test data with mixed GitHub Pages URLs for filtering demonstration

#### Presentation Layer
- **RepositoriesViewModel** - Manages UI state using StateFlow
- **UiState** - Sealed interface with Loading, Success(repositories), and Error(message) states
- Flow-based state management with automatic error handling via `.catch()`

#### UI Layer
- **RepositoriesScreen** - Main screen with LazyColumn
- **RepositoryListItem** - Card composable for each repository
- **NoUserDefined** - Error screen shown when username cannot be determined

#### Platform Layer
- **NavigationDelegate** - Interface for platform-specific navigation
- **WasmNavigationDelegate** - WASM implementation using `window.open()`
- **Platform** - expect/actual for platform utilities
- **PlatformModule** - expect/actual Koin module for platform-specific dependencies

## Development Workflow

### Adding New Features

1. **Domain Layer First**: Define business logic in `domain/` (platform-agnostic)
2. **Data Layer**: Implement data sources in `data/` or platform-specific in `wasmJsMain/`
3. **Presentation Layer**: Create ViewModels in `ui/` with StateFlow for state management
4. **UI Layer**: Build Compose UI components in `ui/`
5. **Platform-Specific**: Use `expect`/`actual` pattern for platform APIs
6. **Dependency Injection**: Register components in appropriate Koin modules

### Koin Module Structure

The app uses modular Koin setup:
- **platformModule** (expect/actual) - Platform-specific dependencies (Navigation, Platform utilities)
- **dataModule** - Data layer (DataSources)
- **domainModule** - Domain layer (UseCases)
- **uiModule** - Presentation layer (ViewModels)
- **appModule** - Root module that includes all others

### Platform-Specific Code Pattern

```kotlin
// commonMain - Interface/expect declaration
expect val platformModule: Module

// wasmJsMain - Actual implementation
actual val platformModule: Module = module {
    singleOf(::WasmNavigationDelegate) { bind<NavigationDelegate>() }
}
```

### State Management Pattern

ViewModels use Flow-based reactive state:
```kotlin
val state: StateFlow<UiState> = flow {
    emit(UiState.Loading)
    val data = useCase.execute()
    emit(UiState.Success(data))
}.catch {
    emit(UiState.Error(it.message ?: "Unknown error"))
}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)
```

### Future: GitHub API Integration

When ready to add real GitHub API:
- Use GitHub REST API v3: `GET /users/{username}/repos`
- Replace `FakeRepositoryDataSource` with Ktor-based implementation
- Add Ktor client dependencies to `libs.versions.toml`
- Create `GitHubRepositoryDataSource` in `data/datasource/`
- Map API response to domain `Repository` model
- Handle rate limiting (60 req/hour unauthenticated, 5000 authenticated)

## Testing

- Tests are located in `commonTest` source set
- Use `./gradlew composeApp:wasmJsBrowserTest` to run browser-based tests
- Use `./gradlew composeApp:allTests` to run all tests and generate an aggregated report