package com.tgyuu.soccerfriends.feature.team.addmember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMemberViewModel @Inject constructor() : ViewModel() {
    private val _eventFlow = MutableSharedFlow<AddMemberEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: AddMemberEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun complete() = event(AddMemberEvent.Complete)

    fun reset() = event(AddMemberEvent.Reset)

    sealed class AddMemberEvent {
        object Complete : AddMemberEvent()
        object Reset : AddMemberEvent()
    }
}