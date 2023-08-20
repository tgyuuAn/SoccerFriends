package com.tgyuu.presentation.feature.team.recyclerview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.entity.Member
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdapterViewModel @Inject constructor() : ViewModel() {
    private val _eventFlow = MutableSharedFlow<AdapterEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: AdapterEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun clickMore(member: Member) = event(AdapterEvent.ClickMore(member))

    sealed class AdapterEvent {
        data class ClickMore(val member: Member) : AdapterEvent()
    }
}