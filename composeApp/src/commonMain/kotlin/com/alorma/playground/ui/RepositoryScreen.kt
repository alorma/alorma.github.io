package com.alorma.playground.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RepositoryScreen(
  username: String,
  viewModel: RepositoryViewModel = koinViewModel()
) {
  Box(
    modifier = Modifier.fillMaxSize().padding(16.dp),
    contentAlignment = Alignment.Center
  ) {
    Text("Repository Screen for user: $username")
  }
}
