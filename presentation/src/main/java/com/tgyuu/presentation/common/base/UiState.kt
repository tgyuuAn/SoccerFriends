package com.tgyuu.presentation.common.base

sealed class UiState<out T> {
    object Init : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<out R>(val data : R) : UiState<R>()
    data class Error(val message : String) : UiState<Nothing>()
}