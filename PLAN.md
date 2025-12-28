# GitHub Pages Repository Aggregator - Implementation Plan

## Current State

**Existing Structure:**
- Simple Compose Multiplatform WASM project
- App.kt is currently empty (just MaterialTheme wrapper)
- No dependencies for networking or data handling yet
- No platform-specific implementations in wasmJsMain
- Uses Kotlin 2.3.0, Compose Multiplatform 1.9.3
- Already has lifecycle-viewmodel-compose and lifecycle-runtime-compose

## Requirements

**Tech Stack:**
- UI: Standard Compose Material3 components
- Data Layer: Ktor HTTP client
- Architecture: MVVM (Model-View-ViewModel)
- Dependency Injection: Koin Multiplatform
- State Management: ViewModel with StateFlow

**Features:**
- Query GitHub repositories for user "alorma"
- Filter repos with GitHub Pages enabled
- Filter repos with URLs containing "alorma.github.io"
- Display as simple list with:
  - Repo title
  - Repo pages URL as subtitle
  - Click action to open URL

**UI Design:**
- Scaffold with LazyColumn
- Simple list items showing title and URL

## Confirmed Approach

✅ **UI**: Standard Compose Material3 components (Scaffold, LazyColumn, ListItem)
✅ **API**: GitHub REST API v3 - GET /users/alorma/repos
✅ **Filtering**: Check "homepage" field contains "alorma.github.io"
✅ **Architecture**: MVVM with ViewModel and StateFlow
✅ **Dependency Injection**: Koin Multiplatform

## Implementation Plan

### Step 1: Update Dependencies

**Files to modify:**
- `gradle/libs.versions.toml` - Add versions for Ktor and kotlinx-serialization
- `composeApp/build.gradle.kts` - Add dependencies

**New dependencies needed:**
- Ktor client core
- Ktor client for WASM (JS engine)
- Ktor content negotiation
- Ktor serialization (kotlinx.json)
- kotlinx-serialization-json
- kotlinx-serialization plugin
- Koin core
- Koin compose multiplatform

**Versions:**
- Ktor: 3.0.3 (latest with WASM support)
- kotlinx-serialization: 1.7.3
- Koin: 4.0.0 (multiplatform support)

### Step 2: Data Layer (Model)

**Create: `composeApp/src/commonMain/kotlin/com/alorma/playground/data/model/GitHubRepository.kt`**
```kotlin
@Serializable
data class GitHubRepository(
    val name: String,
    val full_name: String,
    val html_url: String,
    val homepage: String?,
    val description: String?
)
```

**Create: `composeApp/src/commonMain/kotlin/com/alorma/playground/data/api/GitHubApi.kt`**
- Interface defining `suspend fun getRepositories(username: String): List<GitHubRepository>`

**Create: `composeApp/src/commonMain/kotlin/com/alorma/playground/data/api/GitHubApiImpl.kt`**
- Implement using Ktor HttpClient
- Endpoint: `https://api.github.com/users/alorma/repos`
- Configure JSON serialization
- Handle errors gracefully

### Step 3: Dependency Injection Setup (Koin)

**Create: `composeApp/src/commonMain/kotlin/com/alorma/playground/di/AppModule.kt`**
```kotlin
val appModule = module {
    single<GitHubApi> { GitHubApiImpl(get()) }
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    viewModel { RepositoryListViewModel(get()) }
}
```

**Update: `composeApp/src/commonMain/kotlin/com/alorma/playground/main.kt`**
- Initialize Koin before starting the app
```kotlin
fun main() {
    startKoin {
        modules(appModule)
    }
    ComposeViewport {
        App()
    }
}
```

### Step 4: Presentation Layer (ViewModel)

**Create: `composeApp/src/commonMain/kotlin/com/alorma/playground/presentation/RepositoryListViewModel.kt`**
```kotlin
class RepositoryListViewModel(
    private val api: GitHubApi
) : ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state

    init {
        loadRepositories()
    }

    fun loadRepositories() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val repos = api.getRepositories("alorma")
                val filtered = repos.filter {
                    it.homepage?.contains("alorma.github.io") == true
                }
                _state.value = UiState.Success(filtered)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Success(val repositories: List<GitHubRepository>) : UiState()
    data class Error(val message: String) : UiState()
}
```

### Step 5: View Layer (UI)

**Update: `composeApp/src/commonMain/kotlin/com/alorma/playground/App.kt`**
- Create Scaffold with TopAppBar
- Use LazyColumn for repository list
- Show loading/error states
- Inject ViewModel using Koin (`koinViewModel()`)
- Collect state using `collectAsState()`

**Create: `composeApp/src/commonMain/kotlin/com/alorma/playground/ui/RepositoryListItem.kt`**
```kotlin
@Composable
fun RepositoryListItem(
    repository: GitHubRepository,
    onItemClick: (String) -> Unit
) {
    ListItem(
        headlineContent = { Text(repository.name) },
        supportingContent = { Text(repository.homepage ?: "") },
        modifier = Modifier.clickable {
            repository.homepage?.let { onItemClick(it) }
        }
    )
}
```

### Step 6: Platform-Specific URL Opening

**Create: `composeApp/src/commonMain/kotlin/com/alorma/playground/platform/UrlOpener.kt`**
```kotlin
expect fun openUrl(url: String)
```

**Create: `composeApp/src/wasmJsMain/kotlin/com/alorma/playground/platform/UrlOpener.kt`**
```kotlin
actual fun openUrl(url: String) {
    kotlinx.browser.window.open(url, "_blank")
}
```

### Step 7: Update CLAUDE.md

**Update: `CLAUDE.md`**
- Add Ktor, kotlinx-serialization, and Koin to dependencies section
- Document the MVVM architecture pattern with ViewModel
- Add GitHub API integration details
- Document Koin dependency injection setup
- Document the filtering logic for GitHub Pages repos
- Add notes about expect/actual for platform-specific code (URL opening)

## File Structure

```
composeApp/src/
├── commonMain/kotlin/com/alorma/playground/
│   ├── App.kt (UPDATED - Main UI with Scaffold + LazyColumn)
│   ├── main.kt (UPDATED - Initialize Koin)
│   ├── data/
│   │   ├── model/
│   │   │   └── GitHubRepository.kt (NEW)
│   │   └── api/
│   │       ├── GitHubApi.kt (NEW)
│   │       └── GitHubApiImpl.kt (NEW)
│   ├── di/
│   │   └── AppModule.kt (NEW - Koin module)
│   ├── presentation/
│   │   └── RepositoryListViewModel.kt (NEW - ViewModel)
│   ├── ui/
│   │   └── RepositoryListItem.kt (NEW)
│   └── platform/
│       └── UrlOpener.kt (NEW - expect)
└── wasmJsMain/kotlin/com/alorma/playground/
    └── platform/
        └── UrlOpener.kt (NEW - actual)

gradle/
└── libs.versions.toml (UPDATED - add Ktor, serialization, Koin)

composeApp/
└── build.gradle.kts (UPDATED - add dependencies, serialization plugin)

CLAUDE.md (UPDATED - add implementation details)
```

## Implementation Order

1. Update `libs.versions.toml` with new versions (Ktor, kotlinx-serialization, Koin)
2. Update `composeApp/build.gradle.kts` with dependencies and serialization plugin
3. Create data models (`GitHubRepository.kt`)
4. Create API interface and implementation (`GitHubApi.kt`, `GitHubApiImpl.kt`)
5. Create Koin DI module (`AppModule.kt`)
6. Create ViewModel with state management (`RepositoryListViewModel.kt`)
7. Update `main.kt` to initialize Koin
8. Create platform-specific URL opener (expect/actual)
9. Create UI components (`RepositoryListItem.kt`)
10. Update `App.kt` with main UI and Koin integration
11. Update `CLAUDE.md` with new architecture details

## Testing Strategy

- Build and run the WASM app: `./gradlew composeApp:wasmJsBrowserDevelopmentRun`
- Verify GitHub API calls work in browser console
- Test filtering logic with different repositories
- Test URL opening in new browser tabs
