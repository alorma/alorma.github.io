package com.alorma.playground.domain.datasource

import com.alorma.playground.domain.model.Repository

interface RepositoryDataSource {
  suspend fun getRepositories(username: String): List<Repository>
}
