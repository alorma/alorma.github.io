package com.alorma.playground

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alorma.playground.data.repository.SettingsRepository
import com.alorma.playground.di.appModule
import com.alorma.playground.ui.RepositoriesScreen
import com.alorma.playground.ui.theme.AppTheme
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

@Suppress("ModifierTopMost")
@OptIn(KoinExperimentalAPI::class)
@Composable
fun App(
  username: String,
  modifier: Modifier = Modifier,
) {
  Box(modifier) {
    KoinMultiplatformApplication(
      config = koinConfiguration {
        modules(appModule)
        properties(mapOf("username" to username))
      },
    ) {
      AppContent(username = username)
    }
  }
}

@Composable
private fun AppContent(
  username: String,
  modifier: Modifier = Modifier,
  settingsRepository: SettingsRepository = koinInject(),
) {
  Box(modifier) {
    AppTheme(settingsRepository) {
      RepositoriesScreen(
        username = username,
        isDarkMode = settingsRepository.darkMode.value,
        onToggleDarkMode = { settingsRepository.toggle() }
      )
    }
  }
}