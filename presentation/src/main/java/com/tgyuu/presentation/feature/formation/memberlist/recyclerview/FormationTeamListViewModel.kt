package com.tgyuu.presentation.feature.formation.memberlist.recyclerview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormationTeamListViewModel @Inject constructor() : ViewModel() {

    sealed class FormationTeamListEvent {
        data class DragStart(val id: Int) : FormationTeamListEvent()
    }

    private val _eventFlow = MutableSharedFlow<FormationTeamListEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: FormationTeamListEvent) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    fun dragStart(memberId: Int) = event(FormationTeamListEvent.DragStart(memberId))
}