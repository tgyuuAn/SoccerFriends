package com.tgyuu.soccerfriends.feature.scoreboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreBoardViewModel @Inject constructor() : ViewModel() {

    private val _eventFlow = MutableSharedFlow<ScoreBoardEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event : ScoreBoardEvent){
        viewModelScope.launch{
            _eventFlow.emit(event)
        }
    }

    fun clickButton() = event(ScoreBoardEvent.ClickButton)

    sealed class ScoreBoardEvent(){
        object ClickButton : ScoreBoardEvent()
    }

}