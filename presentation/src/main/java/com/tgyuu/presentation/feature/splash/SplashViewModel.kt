package com.tgyuu.presentation.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    init {
        splashMillis(2000L)
    }

    private val _eventFlow = MutableSharedFlow<SplashEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: SplashEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun splashMillis(delayMillis: Long) = viewModelScope.launch {
        delay(delayMillis)
        event(SplashEvent.Splash)
    }

    sealed class SplashEvent {
        object Splash : SplashEvent()
    }
}