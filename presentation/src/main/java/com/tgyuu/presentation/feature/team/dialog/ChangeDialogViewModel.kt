package com.tgyuu.presentation.feature.team.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeDialogViewModel @Inject constructor() : ViewModel() {
    private val _eventFlow = MutableSharedFlow<DialogEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: DialogEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun cancel() = event(DialogEvent.Cancel)

    fun changeTeamName() = event(DialogEvent.ChangeTeamName)

    sealed class DialogEvent {
        object Cancel : DialogEvent()
        object ChangeTeamName : DialogEvent()
    }

}