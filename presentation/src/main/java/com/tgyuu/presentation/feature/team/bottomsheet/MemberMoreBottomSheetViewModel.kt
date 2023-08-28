package com.tgyuu.presentation.feature.team.bottomsheet

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
class MemberMoreBottomSheetViewModel @Inject constructor(): ViewModel() {

    private val _eventFlow = MutableSharedFlow<MemberMoreEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: MemberMoreEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun complete() = event(MemberMoreEvent.Complete)

    sealed class MemberMoreEvent {
        object Complete : MemberMoreEvent()
    }

    private val _bottomsheetFlag = MutableStateFlow(MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_NICKNAME)
    val bottomsheetFlag = _bottomsheetFlag.asStateFlow()

    fun changeNickName() {
        _bottomsheetFlag.value = MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_NICKNAME
    }

    fun changeBackNumber() {
        _bottomsheetFlag.value = MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_BACKNUMBER
    }

    fun changePosition() {
        _bottomsheetFlag.value = MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_POSITION
    }

    fun removeMember() {
        _bottomsheetFlag.value = MemberMoreBottomSheetFragment.BottomSheetFlag.REMOVE_MEMBER
    }

    fun changeImage() {
        _bottomsheetFlag.value = MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_IMAGE
    }

    fun removeImage() {
        _bottomsheetFlag.value = MemberMoreBottomSheetFragment.BottomSheetFlag.REMOVE_IMAGE
    }

    fun changeIsBenchWarmer() {
        _bottomsheetFlag.value = MemberMoreBottomSheetFragment.BottomSheetFlag.CHANGE_ISBENCHWARMER
    }
}