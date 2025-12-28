# Kotlin Multiplatform Web Application with Compose and WebAssembly

A Kotlin Multiplatform project targeting the web using WebAssembly (wasm-js) with Compose Multiplatform.

## Project Structure

```
├── build.gradle.kts          # Build configuration with Compose plugin
├── settings.gradle.kts        # Project settings
├── gradle.properties          # Gradle properties
├── src/
│   ├── commonMain/kotlin/     # Shared Kotlin code
│   │   └── com/alorma/playground/
│   │       └── Greeting.kt    # Common greeting logic
│   └── wasmJsMain/            # WebAssembly-specific code
│       ├── kotlin/
│       │   └── com/alorma/playground/
│       │       ├── Main.kt    # Compose application entry point
│       │       └── Platform.kt # WASM platform implementation
│       └── resources/
│           └── index.html     # HTML template with canvas element
```

## Features

- ✅ Kotlin Multiplatform with WebAssembly (wasm-js) target
- ✅ Compose Multiplatform for web UI
- ✅ Common code shared across platforms
- ✅ Platform-specific implementations
- ✅ Modern declarative UI with Compose
- ✅ Canvas-based rendering for high performance
- ✅ WebAssembly for near-native performance in the browser

## Building the Project

### Prerequisites

- JDK 11 or higher

### Build Commands

```bash
# Build the project
./gradlew build

# Run in development mode (with continuous compilation)
./gradlew wasmJsBrowserDevelopmentRun --continuous

# Build for production
./gradlew wasmJsBrowserProductionWebpack

# Run production build
./gradlew wasmJsBrowserProductionRun
```

## Running the Application

After building, the compiled WebAssembly and HTML files will be in:
- Development: `build/dist/wasmJs/developmentExecutable/`
- Production: `build/dist/wasmJs/productionExecutable/`

Open `index.html` in a browser that supports WebAssembly to see the application.

## Package Structure

All code is organized under the `com.alorma.playground` package.

## Technology Stack

- Kotlin 1.9.21
- Compose Multiplatform 1.6.0-alpha01
- Kotlin/Wasm-JS (WebAssembly)
- Gradle 8.5
- Compose for Web (declarative UI with canvas rendering)
- Skiko 0.7.89.1 (Skia graphics engine for Kotlin)

## Development

The project uses Compose Multiplatform's experimental WebAssembly support, which provides better performance than traditional JavaScript compilation. The UI is rendered on a canvas element using Skiko (Skia for Kotlin), providing a consistent cross-platform rendering experience.

### Known Issue & Workaround

Due to an issue with early Compose for Wasm support, the Skiko resources need to be manually copied for production builds. After running `./gradlew compileProductionExecutableKotlinWasmJs`, execute:

```bash
# Extract and copy Skiko resources
jar xf ~/.gradle/caches/modules-2/files-2.1/org.jetbrains.skiko/skiko-js-wasm-runtime/0.7.89.1/*/skiko-js-wasm-runtime-0.7.89.1.jar skiko.mjs skiko.wasm
mkdir -p build/js/packages/alormaWebApp/kotlin
cp skiko.* build/js/packages/alormaWebApp/kotlin/
rm skiko.mjs skiko.wasm

# Then run webpack
./gradlew wasmJsBrowserProductionWebpack
```

This workaround will not be needed in future versions of Compose Multiplatform as the tooling matures.

**Note**: Development builds using `wasmJsBrowserDevelopmentRun` should work without this workaround.
