plugins {
    kotlin("multiplatform") version "1.9.21"
    id("org.jetbrains.compose") version "1.6.0-alpha01"
}

group = "com.alorma.playground"
version = "1.0.0"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "alormaWebApp"
        browser {
            commonWebpackConfig {
                outputFileName = "alormaWebApp.js"
            }
        }
        binaries.executable()
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
            }
        }
        
        val wasmJsMain by getting {
            dependencies {
            }
        }
    }
}

// Workaround for skiko.mjs issue in wasm builds
tasks.register<Copy>("copySkikoResources") {
    val webpackDir = File(project.layout.buildDirectory.asFile.get(), "js/packages/${project.name}/kotlin")
    
    from(provider {
        configurations.named("wasmJsRuntimeClasspath").get().files.filter { 
            it.name.contains("skiko-js-wasm-runtime")
        }.map { zipTree(it) }
    })
    
    into(webpackDir)
    include("*.mjs", "*.wasm")
    
    doFirst {
        webpackDir.mkdirs()
    }
}

afterEvaluate {
    tasks.withType<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>().configureEach {
        dependsOn("copySkikoResources")
    }
    tasks.named("wasmJsProductionExecutableCompileSync").configure {
        finalizedBy("copySkikoResources")
    }
}
