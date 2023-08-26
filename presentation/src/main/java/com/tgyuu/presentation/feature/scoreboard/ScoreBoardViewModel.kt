package com.tgyuu.presentation.feature.scoreboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.usecase.GetTeamUseCase
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.common.di.IO
import com.tgyuu.presentation.feature.scoreboard.ScoreBoardFragment.Companion.LONG_TO_SECOND
import com.tgyuu.presentation.feature.scoreboard.ScoreBoardFragment.Companion.MAX_VALUE
import com.tgyuu.presentation.feature.scoreboard.ScoreBoardFragment.Companion.MIN_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import javax.inject.Inject

@HiltViewModel
class ScoreBoardViewModel @Inject constructor(
    private val getTeamUseCase: GetTeamUseCase,
    @IO private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    sealed class ScoreBoardEvent {
        object ClickPause : ScoreBoardEvent()
        object ChangeAwayTeamImage : ScoreBoardEvent()
        data class Error(val message : String) : ScoreBoardEvent()
    }

    sealed class GameState {
        object GameNotStarted : GameState()
        object GameInProgress : GameState()
        data class GameResult(val score: Pair<Int, Int>) : GameState()
    }

    private val _eventFlow = MutableSharedFlow<ScoreBoardEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _team = MutableStateFlow<UiState<Team>>(UiState.Init)
    val team = _team.asStateFlow()

    private val _gameState = MutableStateFlow<GameState>(GameState.GameNotStarted)
    val gameState = _gameState.asStateFlow()

    private val _playTime = MutableStateFlow<Int>(0)
    val playTime = _playTime.asStateFlow()

    private val _alarmTime = MutableStateFlow<Int>(0)
    val alarmTime = _alarmTime.asStateFlow()

    private val _timer = MutableStateFlow<Long>(0)
    val timer = _timer.asStateFlow()

    private val _homeTeamScore = MutableStateFlow<Int>(0)
    val homeTeamScore = _homeTeamScore.asStateFlow()

    private val _awayTeamScore = MutableStateFlow<Int>(0)
    val awayTeamScore = _awayTeamScore.asStateFlow()

    var timerJob: Job? = null

    private fun event(event: ScoreBoardEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun setTeamState(uiState: UiState<Team>) {
        _team.value = uiState
    }

    private fun setGameState(gameState: GameState) {
        _gameState.value = gameState
    }

    fun getTeam() {
        setTeamState(UiState.Loading)

        viewModelScope.launch(ioDispatcher) {
            getTeamUseCase().collect {
                setTeamState(UiState.Success(it))
            }
        }
    }

    fun changeAwayTeamImage() {
        event(ScoreBoardEvent.ChangeAwayTeamImage)
    }

    fun resetAllValue() {
        resetTimer()
        _homeTeamScore.value = 0
        _awayTeamScore.value = 0
    }

    fun initTimer() {
        _timer.value = (_playTime.value * 60 * 1000).toLong()
    }

    fun startTimer() {
        timerJob = viewModelScope.launch(ioDispatcher, CoroutineStart.LAZY) {
            if (_timer.value == 0L) initTimer()

            var oldTimeMills = System.currentTimeMillis()

            while (_timer.value > 0) {
                val delayMills = System.currentTimeMillis() - oldTimeMills

                if (delayMills >= LONG_TO_SECOND) {
                    _timer.value = _timer.value - LONG_TO_SECOND
                    oldTimeMills = System.currentTimeMillis()
                }

                yield()
            }

            if (_timer.value <= 0) {
                setGameState(GameState.GameResult(Pair(_homeTeamScore.value, _awayTeamScore.value)))
            }
        }
        timerJob!!.start()
    }

    fun pauseTimer() {
        if (timerJob != null) timerJob!!.cancel()
        timerJob = null
    }

    fun resetTimer() {
        if (timerJob != null) timerJob!!.cancel()
        timerJob = null
        _timer.value = 0
    }

    fun clickButton() {
        when (_gameState.value) {
            GameState.GameNotStarted -> {
                if (_playTime.value == 0) {
                    event(ScoreBoardEvent.Error("경기 시간은 반드시 1분 이상이어야 합니다."))
                    return
                }

                if (_playTime.value == alarmTime.value) {
                    event(ScoreBoardEvent.Error("알람 시간은 경기 시간보다 작아야 합니다."))
                    return
                }
                setGameState(GameState.GameInProgress)
            }

            GameState.GameInProgress -> setGameState(
                GameState.GameResult(
                    Pair(
                        _homeTeamScore.value,
                        _awayTeamScore.value
                    )
                )
            )

            is GameState.GameResult -> setGameState(GameState.GameNotStarted)
        }
    }

    fun clickPuase() {
        if (timerJob == null) startTimer()
        else pauseTimer()

        event(ScoreBoardEvent.ClickPause)
    }

    fun clickPlusPlayTime() {
        if (_playTime.value + 1 <= MAX_VALUE)
            _playTime.value = _playTime.value.plus(1)
    }

    fun clickMinusPlayTime() {
        if (_alarmTime.value == _playTime.value) {
            return
        }

        if (_playTime.value - 1 >= MIN_VALUE)
            _playTime.value = _playTime.value.minus(1)
    }

    fun clickPlusAlarmTime() {
        if (_alarmTime.value == _playTime.value) {
            return
        }

        if (_alarmTime.value + 1 <= MAX_VALUE)
            _alarmTime.value = _alarmTime.value.plus(1)
    }

    fun clickMinusAlarmTime() {
        if (_alarmTime.value - 1 >= MIN_VALUE)
            _alarmTime.value = _alarmTime.value.minus(1)
    }

    fun clickPlusHomeTeamScore() {
        if (_homeTeamScore.value + 1 <= MAX_VALUE)
            _homeTeamScore.value = _homeTeamScore.value.plus(1)
    }

    fun clickMinusHomeTeamScore() {
        if (_homeTeamScore.value - 1 >= MIN_VALUE)
            _homeTeamScore.value = _homeTeamScore.value.minus(1)
    }

    fun clickPlusAwayTeamScore() {
        if (_awayTeamScore.value + 1 <= MAX_VALUE)
            _awayTeamScore.value = _awayTeamScore.value.plus(1)
    }

    fun clickMinusAwayTeamScore() {
        if (_awayTeamScore.value - 1 >= MIN_VALUE)
            _awayTeamScore.value = _awayTeamScore.value.minus(1)
    }
}