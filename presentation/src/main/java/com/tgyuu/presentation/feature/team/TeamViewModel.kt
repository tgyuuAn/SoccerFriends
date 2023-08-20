package com.tgyuu.presentation.feature.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor() : ViewModel() {
    private val _eventFlow = MutableSharedFlow<TeamEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: TeamEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun addMember() = event(TeamEvent.AddMember)

    sealed class TeamEvent {
        object AddMember : TeamEvent()
    }
}