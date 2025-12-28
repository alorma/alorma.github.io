package com.alorma.playground

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alorma.playground.di.appModule
import com.alorma.playground.ui.RepositoryScreen
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App(
  modifier: Modifier = Modifier,
  username: String,
) {
  KoinMultiplatformApplication(
    config = koinConfiguration {
      modules(appModule)
      properties(mapOf("username" to username))
    },
  ) {
    MaterialTheme {
      RepositoryScreen(username = username)
    }
  }
}