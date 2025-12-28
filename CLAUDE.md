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

### Project Structure

- **composeApp/** - Main application module
  - **src/commonMain/kotlin/** - Shared Kotlin code for all targets
    - `com.alorma.playground` - Main application package
      - `App.kt` - Main Compose UI entry point
      - `main.kt` - Application entry point using `ComposeViewport`
      - `Greeting.kt` - Example greeting functionality
  - **src/wasmJsMain/kotlin/** - WASM-specific implementations
  - **src/commonMain/resources/** - Web resources (HTML, CSS)

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

## Development Workflow

When adding new features:
1. Add shared code in `composeApp/src/commonMain/kotlin/`
2. Add platform-specific implementations in `composeApp/src/wasmJsMain/kotlin/` if needed
3. Use `expect`/`actual` declarations for platform-specific APIs
4. Resources go in `composeApp/src/commonMain/resources/`
5. Run tests with `./gradlew check` before committing

## Testing

- Tests are located in `commonTest` source set
- Use `./gradlew composeApp:wasmJsBrowserTest` to run browser-based tests
- Use `./gradlew composeApp:allTests` to run all tests and generate an aggregated report