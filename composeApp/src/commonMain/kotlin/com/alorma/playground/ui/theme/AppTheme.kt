package com.alorma.playground.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.alorma.playground.data.repository.SettingsRepository

@Suppress("ModifierRequired")
@Composable
fun AppTheme(
  settingsRepository: SettingsRepository,
  content: @Composable () -> Unit,
) {
  var isDarkMode by remember { settingsRepository.darkMode }

  MaterialTheme(
    colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme(),
    content = content
  )
}
