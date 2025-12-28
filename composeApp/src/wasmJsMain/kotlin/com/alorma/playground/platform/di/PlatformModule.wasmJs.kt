package com.alorma.playground.platform.di

import com.alorma.playground.navigation.NavigationDelegate
import com.alorma.playground.navigation.WasmNavigationDelegate
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
  singleOf(::WasmNavigationDelegate) { bind<NavigationDelegate>() }
}