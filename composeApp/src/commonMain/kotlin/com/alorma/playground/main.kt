package com.alorma.playground

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
  val username = parseUsernameFromUrl()
  ComposeViewport {
    if (username == null) {
      NoUserDefined()
    } else {
      App(username = username)
    }
  }
}

@Composable
private fun NoUserDefined() {
  MaterialTheme {
    Box(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "No user defined. Please access this app from a GitHub Pages URL (e.g., username.github.io)",
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
      )
    }
  }
}

private fun parseUsernameFromUrl(): String? {
  val hostname = window.location.hostname
  // Extract username from subdomain pattern: username.github.io
  if (hostname.endsWith(".github.io")) {
    val username = hostname.removeSuffix(".github.io")
    if (username.isNotEmpty()) {
      return username
    }
  }
  return null
}