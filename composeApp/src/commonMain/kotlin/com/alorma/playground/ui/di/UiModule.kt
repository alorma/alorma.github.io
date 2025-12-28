package com.alorma.playground.ui.di

import com.alorma.playground.ui.RepositoriesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
  viewModelOf(::RepositoriesViewModel)
}
