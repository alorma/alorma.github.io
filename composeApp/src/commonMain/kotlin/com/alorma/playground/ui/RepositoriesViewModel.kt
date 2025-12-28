package com.alorma.playground.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alorma.playground.domain.model.Repository
import com.alorma.playground.domain.usecase.GetGitHubPagesRepositoriesUseCase
import com.alorma.playground.navigation.NavigationDelegate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class RepositoriesViewModel(
  private val username: String,
  private val getGitHubPagesRepositoriesUseCase: GetGitHubPagesRepositoriesUseCase,
  private val navigationDelegate: NavigationDelegate,
) : ViewModel() {

  val state: StateFlow<UiState> = flow {
    emit(UiState.Loading)
    val repositories = getGitHubPagesRepositoriesUseCase.obtain(username)
    emit(UiState.Success(repositories))
  }.catch {
    emit(UiState.Error(it.message ?: "Unknown error"))
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5000),
    initialValue = UiState.Loading
  )
}

sealed interface UiState {
  data object Loading : UiState
  data class Success(val repositories: List<Repository>) : UiState
  data class Error(val message: String) : UiState
}
