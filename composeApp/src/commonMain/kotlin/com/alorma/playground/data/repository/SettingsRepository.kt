package com.alorma.playground.data.repository

import androidx.compose.runtime.mutableStateOf
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class SettingsRepository(
  private val settings: Settings
) {
  val darkMode = mutableStateOf(settings.getBoolean(KeyDarkMode, DefaultDark))

  fun toggle() {
    val current = settings.getBoolean(KeyDarkMode, DefaultDark)
    darkMode.value = !current
    settings[KeyDarkMode] = !current
  }

  companion object {
    private const val KeyDarkMode = "dark_mode"
    private const val DefaultDark = false
  }
}
