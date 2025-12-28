package com.alorma.playground.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alorma.playground.domain.model.Repository

@Composable
fun RepositoryListItem(
  repository: Repository,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier,
    shape = MaterialTheme.shapes.large,
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(16.dp)
    ) {
      Text(
        text = repository.name,
        style = MaterialTheme.typography.titleMedium
      )
      repository.description?.let { description ->
        Text(
          text = description,
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier.padding(top = 4.dp)
        )
      }
      Text(
        text = repository.pagesUrl,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp)
      )
    }
  }
}