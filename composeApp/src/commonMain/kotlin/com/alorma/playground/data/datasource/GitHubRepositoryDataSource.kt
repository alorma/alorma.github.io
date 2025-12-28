package com.alorma.playground.data.datasource

import com.alorma.playground.data.model.GitHubRepoResponse
import com.alorma.playground.data.model.toDomain
import com.alorma.playground.domain.datasource.RepositoryDataSource
import com.alorma.playground.domain.model.Repository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GitHubRepositoryDataSource(
  private val httpClient: HttpClient
) : RepositoryDataSource {
  override suspend fun getRepositories(username: String): List<Repository> {
    val response = httpClient.get("https://api.github.com/users/$username/repos")
    val repos: List<GitHubRepoResponse> = response.body()
    return repos.map { it.toDomain() }
  }
}
