package com.alorma.playground.domain.usecase

import com.alorma.playground.domain.datasource.RepositoryDataSource
import com.alorma.playground.domain.model.Repository

class GetGitHubPagesRepositoriesUseCase(
  private val repositoryDataSource: RepositoryDataSource
) {
  suspend fun obtain(username: String): List<Repository> {
    val repositories = repositoryDataSource.getRepositories(username)
    return repositories.filter { repository ->
      repository.pagesUrl.contains("$username.github.io")
    }
  }
}
