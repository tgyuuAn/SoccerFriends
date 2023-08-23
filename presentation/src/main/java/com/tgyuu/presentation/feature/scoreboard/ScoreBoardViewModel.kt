package com.tgyuu.presentation.feature.scoreboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreBoardViewModel @Inject constructor() : ViewModel() {

    private val _eventFlow = MutableSharedFlow<ScoreBoardEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: ScoreBoardEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun clickButton() = event(ScoreBoardEvent.ClickButton)

    private val _playTime = MutableStateFlow<Int>(0)
    val playTime = _playTime.asStateFlow()

    fun clickPlusPlayTime() {
        if (_playTime.value + 1 <= 99)
            _playTime.value = _playTime.value.plus(1)
    }

    fun clickMinusPlayTime() {
        if (_playTime.value - 1 >= 0)
            _playTime.value = _playTime.value.minus(1)
    }

    private val _alarmTime = MutableStateFlow<Int>(0)
    val alarmTime = _alarmTime.asStateFlow()

    fun clickPlusAlarmTime() {
        if(_alarmTime.value == _playTime.value){
            return
        }

        if (_alarmTime.value + 1 <= 99)
            _alarmTime.value = _alarmTime.value.plus(1)
    }

    fun clickMinusAlarmTime() {
        if (_alarmTime.value - 1 >= 0)
            _alarmTime.value = _alarmTime.value.minus(1)
    }

    private val _homeTeamScore = MutableStateFlow<Int>(0)
    val homeTeamScore = _homeTeamScore.asStateFlow()

    fun clickPlusHomeTeamScore() {
        if (_homeTeamScore.value + 1 <= 99)
            _homeTeamScore.value = _homeTeamScore.value.plus(1)
    }

    fun clickMinusHomeTeamScore() {
        if (_homeTeamScore.value - 1 >= 0)
            _homeTeamScore.value = _homeTeamScore.value.minus(1)
    }

    private val _awayTeamScore = MutableStateFlow<Int>(0)
    val awayTeamScore = _awayTeamScore.asStateFlow()

    fun clickPlusAwayTeamScore() {
        if (_awayTeamScore.value + 1 <= 99)
            _awayTeamScore.value = _awayTeamScore.value.plus(1)
    }

    fun clickMinusAwayTeamScore() {
        if (_awayTeamScore.value - 1 >= 0)
            _awayTeamScore.value = _awayTeamScore.value.minus(1)
    }

    sealed class ScoreBoardEvent {
        object ClickButton : ScoreBoardEvent()
    }

}