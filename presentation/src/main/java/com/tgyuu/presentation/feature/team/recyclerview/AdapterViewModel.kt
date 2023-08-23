package com.tgyuu.presentation.feature.team.recyclerview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.usecase.DeleteMemberUseCase
import com.tgyuu.domain.usecase.GetMemberUseCase
import com.tgyuu.domain.usecase.UpdateMemberInformationUseCase
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.common.di.IO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdapterViewModel @Inject constructor(
    private val getMemberUseCase: GetMemberUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val updateMemberInformationUseCase: UpdateMemberInformationUseCase,
    @IO private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    sealed class AdapterEvent {
        data class ClickMore(val member: Member) : AdapterEvent()
    }

    private val _eventFlow = MutableSharedFlow<AdapterEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _memberListFlow = MutableStateFlow<UiState<List<Member>>>(UiState.Init)
    val memberListFlow = _memberListFlow.asStateFlow()

    private fun setMemberListState(uiState: UiState<List<Member>>) {
        _memberListFlow.value = uiState
    }

    private fun event(event: AdapterEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun clickMore(member: Member) = event(AdapterEvent.ClickMore(member))

    fun getMemberList() {
        setMemberListState(UiState.Loading)
        viewModelScope.launch(ioDispatcher) {
            getMemberUseCase.getAllMembers().collect { setMemberListState(UiState.Success(it)) }
        }
    }

    var updateMember: Member? = null

    fun updateMemberImage(image: String) {
        if (updateMember == null) {
            return
        }

        viewModelScope.launch(ioDispatcher) {
            updateMemberInformationUseCase.updateMemberImage(updateMember!!, image)
        }

        getMemberList()
    }

    fun removeMemberImage() {
        if (updateMember == null) {
            return
        }

        viewModelScope.launch(ioDispatcher) {
            updateMemberInformationUseCase.removeMemberImage(updateMember!!)
        }

        getMemberList()
    }

    fun removeMember() {
        if (updateMember == null) {
            return
        }

        viewModelScope.launch(ioDispatcher) {
            deleteMemberUseCase(updateMember!!)
        }

        getMemberList()
    }
}