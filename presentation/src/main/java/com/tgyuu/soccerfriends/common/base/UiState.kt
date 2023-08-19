package com.tgyuu.soccerfriends.common.base

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out R>(val data : R) : UiState<R>()
    data class Error(val message : String) : UiState<Nothing>()
}