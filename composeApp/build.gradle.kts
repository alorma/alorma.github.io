import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.composeMultiplatform)
  alias(libs.plugins.composeCompiler)
  alias(libs.plugins.kotlinx.serialization)
}

kotlin {
  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    browser()
    binaries.executable()
  }

  sourceSets {
    commonMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.foundation)
      implementation(compose.material3)
      implementation(compose.ui)
      implementation(compose.components.resources)
      implementation(compose.components.uiToolingPreview)
      implementation(libs.androidx.lifecycle.viewmodelCompose)
      implementation(libs.androidx.lifecycle.runtimeCompose)

      // Koin
      implementation(libs.koin.compose)
      implementation(libs.koin.compose.viewmodel)

      // Ktor
      implementation(libs.ktor.client.core)
      implementation(libs.ktor.client.content.negotiation)
      implementation(libs.ktor.serialization.kotlinx.json)

      // Serialization
      implementation(libs.kotlinx.serialization.json)

      // Settings
      implementation(libs.multiplatform.settings)
      implementation(libs.multiplatform.settings.noArg)
    }

    wasmJsMain.dependencies {
      // Ktor JS client for WASM
      implementation(libs.ktor.client.js)
    }

    commonTest.dependencies {
      implementation(libs.kotlin.test)
    }
  }
}


