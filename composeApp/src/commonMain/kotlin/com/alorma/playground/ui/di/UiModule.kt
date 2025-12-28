package com.alorma.playground.ui.di

import com.alorma.playground.ui.RepositoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::RepositoryViewModel)
}
