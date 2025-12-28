package com.alorma.playground.data.di

import com.alorma.playground.data.datasource.FakeRepositoryDataSource
import com.alorma.playground.domain.datasource.RepositoryDataSource
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::FakeRepositoryDataSource) { bind<RepositoryDataSource>() }
}
