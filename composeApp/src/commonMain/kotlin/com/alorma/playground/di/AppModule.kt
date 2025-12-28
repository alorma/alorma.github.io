package com.alorma.playground.di

import com.alorma.playground.data.di.dataModule
import com.alorma.playground.domain.di.domainModule
import com.alorma.playground.ui.di.uiModule
import org.koin.dsl.module

val appModule = module {
  includes(dataModule)
  includes(domainModule)
  includes(uiModule)
}
