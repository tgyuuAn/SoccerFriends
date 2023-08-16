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

    fun event(event : HomeEvent){
        viewModelScope.launch{
            _eventFlow.emit(event)
        }
    }

    fun clickButton() = event(HomeEvent.ClickButton)

    sealed class HomeEvent(){
        object ClickButton : HomeEvent()
    }
}