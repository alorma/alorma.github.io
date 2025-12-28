package com.alorma.playground.domain.usecase

import com.alorma.playground.domain.datasource.RepositoryDataSource
import com.alorma.playground.domain.model.Repository

class GetGitHubPagesRepositoriesUseCase(
  private val repositoryDataSource: RepositoryDataSource
) {
  suspend fun obtain(username: String): List<Repository> {
    val repositories = repositoryDataSource.getRepositories(username)
    val rootPagesUrl = "https://$username.github.io"

    return repositories.filter { repository ->
      val url = repository.pagesUrl.trimEnd('/')
      // Include repos with pages URLs containing username.github.io
      // but exclude the root repository (username.github.io itself)
      repository.pagesUrl.contains("$username.github.io") && url != rootPagesUrl
    }
  }
}
