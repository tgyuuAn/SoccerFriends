package com.tgyuu.soccerfriends.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: HomeEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun moveToScoreBoard() = event(HomeEvent.MoveToScoreBoard)
    fun moveToFormation() = event(HomeEvent.MoveToFormation)
    fun moveToTeamManagement() = event(HomeEvent.MoveToTeamManagement)

    sealed class HomeEvent {
        object MoveToScoreBoard : HomeEvent()
        object MoveToFormation : HomeEvent()
        object MoveToTeamManagement : HomeEvent()
    }
}