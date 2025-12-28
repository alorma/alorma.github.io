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
- Architecture: MVP (Model-View-Presenter)

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
✅ **Architecture**: MVP pattern

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

**Versions:**
- Ktor: 3.0.3 (latest with WASM support)
- kotlinx-serialization: 1.7.3

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

### Step 3: Presentation Layer (Presenter)

**Create: `composeApp/src/commonMain/kotlin/com/alorma/playground/presentation/RepositoryListPresenter.kt`**
```kotlin
class RepositoryListPresenter(private val api: GitHubApi) {
    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state

    suspend fun loadRepositories() {
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

sealed class UiState {
    object Loading : UiState()
    data class Success(val repositories: List<GitHubRepository>) : UiState()
    data class Error(val message: String) : UiState()
}
```

### Step 4: View Layer (UI)

**Update: `composeApp/src/commonMain/kotlin/com/alorma/playground/App.kt`**
- Create Scaffold with TopAppBar
- Use LazyColumn for repository list
- Show loading/error states
- Initialize presenter and collect state
- Use lifecycle-aware composition

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

### Step 5: Platform-Specific URL Opening

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

### Step 6: Update CLAUDE.md

**Update: `CLAUDE.md`**
- Add Ktor and kotlinx-serialization to dependencies section
- Document the MVP architecture pattern
- Add GitHub API integration details
- Document the filtering logic for GitHub Pages repos
- Add notes about expect/actual for platform-specific code (URL opening)

## File Structure

```
composeApp/src/
├── commonMain/kotlin/com/alorma/playground/
│   ├── App.kt (UPDATED - Main UI with Scaffold + LazyColumn)
│   ├── main.kt (NO CHANGE)
│   ├── data/
│   │   ├── model/
│   │   │   └── GitHubRepository.kt (NEW)
│   │   └── api/
│   │       ├── GitHubApi.kt (NEW)
│   │       └── GitHubApiImpl.kt (NEW)
│   ├── presentation/
│   │   └── RepositoryListPresenter.kt (NEW)
│   ├── ui/
│   │   └── RepositoryListItem.kt (NEW)
│   └── platform/
│       └── UrlOpener.kt (NEW - expect)
└── wasmJsMain/kotlin/com/alorma/playground/
    └── platform/
        └── UrlOpener.kt (NEW - actual)

gradle/
└── libs.versions.toml (UPDATED - add Ktor, serialization)

composeApp/
└── build.gradle.kts (UPDATED - add dependencies, serialization plugin)

CLAUDE.md (UPDATED - add implementation details)
```

## Implementation Order

1. Update `libs.versions.toml` with new versions
2. Update `composeApp/build.gradle.kts` with dependencies and plugin
3. Create data models (`GitHubRepository.kt`)
4. Create API interface and implementation (`GitHubApi.kt`, `GitHubApiImpl.kt`)
5. Create presenter with state management (`RepositoryListPresenter.kt`)
6. Create platform-specific URL opener (expect/actual)
7. Create UI components (`RepositoryListItem.kt`)
8. Update `App.kt` with main UI
9. Update `CLAUDE.md` with new architecture details

## Testing Strategy

- Build and run the WASM app: `./gradlew composeApp:wasmJsBrowserDevelopmentRun`
- Verify GitHub API calls work in browser console
- Test filtering logic with different repositories
- Test URL opening in new browser tabs
