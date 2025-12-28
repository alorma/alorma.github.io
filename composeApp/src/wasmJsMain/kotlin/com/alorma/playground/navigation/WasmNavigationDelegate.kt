package com.alorma.playground.navigation

import kotlinx.browser.window

class WasmNavigationDelegate : NavigationDelegate {
  override fun openUrl(url: String) {
    window.open(url, "_blank")
  }
}
