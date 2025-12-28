package com.alorma.playground.domain.di

import com.alorma.playground.domain.usecase.GetGitHubPagesRepositoriesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
  factoryOf(::GetGitHubPagesRepositoriesUseCase)
}
