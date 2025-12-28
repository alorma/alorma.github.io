package com.alorma.playground.data.di

import com.alorma.playground.data.datasource.GitHubRepositoryDataSource
import com.alorma.playground.domain.datasource.RepositoryDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
  single {
    HttpClient {
      install(ContentNegotiation) {
        json(
          Json {
            ignoreUnknownKeys = true
            isLenient = true
          }
        )
      }
    }
  }

  singleOf(::GitHubRepositoryDataSource) { bind<RepositoryDataSource>() }
}
