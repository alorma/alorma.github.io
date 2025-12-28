package com.alorma.playground.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RepositoriesScreen(
  username: String,
  modifier: Modifier = Modifier,
  viewModel: RepositoriesViewModel = koinViewModel { parametersOf(username) },
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  Box(
    modifier = modifier.fillMaxSize().padding(16.dp),
    contentAlignment = Alignment.Center
  ) {
    when (val currentState = state) {
      is UiState.Loading -> {
        CircularProgressIndicator()
      }

      is UiState.Success -> {
        Text("Found ${currentState.repositories.size} repositories for user: $username")
      }

      is UiState.Error -> {
        Text("Error: ${currentState.message}")
      }
    }
  }
}
