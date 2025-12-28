package com.alorma.playground.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alorma.playground.ui.icons.AppIcons
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoriesScreen(
  username: String,
  isDarkMode: Boolean,
  onToggleDarkMode: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: RepositoriesViewModel = koinViewModel { parametersOf(username) },
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = { Text("$username's GitHub Pages") },
        actions = {
          IconButton(onClick = onToggleDarkMode) {
            Icon(
              imageVector = if (isDarkMode) AppIcons.LightMode else AppIcons.DarkMode,
              contentDescription = if (isDarkMode) "Switch to light mode" else "Switch to dark mode"
            )
          }
        }
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentAlignment = Alignment.Center
    ) {
      when (val currentState = state) {
        is UiState.Loading -> {
          CircularProgressIndicator()
        }

        is UiState.Success -> {
          LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(currentState.repositories) { repository ->
              RepositoryListItem(
                repository = repository,
                onClick = { viewModel.onRepositoryClick(repository) },
                modifier = Modifier.fillMaxWidth()
              )
            }
          }
        }

        is UiState.Error -> {
          Text("Error: ${currentState.message}")
        }
      }
    }
  }
}
