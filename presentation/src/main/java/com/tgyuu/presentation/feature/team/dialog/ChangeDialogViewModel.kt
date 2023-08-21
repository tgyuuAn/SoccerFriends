package com.tgyuu.presentation.feature.team.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.usecase.UpdateMemberInformationUseCase
import com.tgyuu.domain.usecase.UpdateTeamInformationUseCase
import com.tgyuu.domain.usecase.ValidateMemberFormatUseCase
import com.tgyuu.domain.usecase.ValidateTeamFormatUseCase
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
class ChangeDialogViewModel @Inject constructor(
    private val validateTeamFormatUseCase: ValidateTeamFormatUseCase,
    private val updateTeamInformationUseCase: UpdateTeamInformationUseCase,
    private val updateMemberInformationUseCase: UpdateMemberInformationUseCase,
    private val validateMemberFormatUseCase: ValidateMemberFormatUseCase,
    @IO private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<DialogEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: DialogEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun cancel() = event(DialogEvent.Cancel)

    fun clickComplete() = event(DialogEvent.ClickComplete)

    sealed class DialogEvent {
        object Cancel : DialogEvent()
        object ClickComplete : DialogEvent()
    }

    private val _team = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val team = _team.asStateFlow()

    private fun setTeamNameState(uiState: UiState<Unit>) {
        _team.value = uiState
    }

    fun changeTeamName(team: Team, teamName: String) {
        setTeamNameState(UiState.Loading)

        if (!validateTeamFormatUseCase(teamName)) {
            setTeamNameState(UiState.Error("최소 한 글자 이상의 팀 명으로 설정해주세요"))
            return
        }

        viewModelScope.launch(ioDispatcher) {
            updateTeamInformationUseCase.updateTeamName(team, teamName)
        }
        setTeamNameState(UiState.Success(Unit))
    }

    private val _member = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val member = _member.asStateFlow()

    private fun setMemberState(uiState: UiState<Unit>) {
        _member.value = uiState
    }

    fun updateMemberNumber(member: Member, number: String) {
        setMemberState(UiState.Loading)

        if (!validateMemberFormatUseCase.isNumeric(number)) {
            setMemberState(UiState.Error("등 번호는 숫자만 가능합니다."))
            return
        }

        viewModelScope.launch(ioDispatcher) {
            updateMemberInformationUseCase.updateMemberNumber(member, number.toInt())
        }
        setMemberState(UiState.Success(Unit))
    }

    fun updateMemberPosition(member: Member, position: String) {
        setMemberState(UiState.Loading)

        if (!validateMemberFormatUseCase.validateMemberPosition(position)) {
            setMemberState(UiState.Error("최소 한 글자 이상의 포지션으로 설정해주세요"))
            return
        }

        viewModelScope.launch(ioDispatcher) {
            updateMemberInformationUseCase.updateMemberPosition(member, position)
        }
        setMemberState(UiState.Success(Unit))
    }

    fun updateMemberName(member: Member, name: String) {
        setMemberState(UiState.Loading)
        if (!validateMemberFormatUseCase.validateMemberName(name)) {
            setMemberState(UiState.Error("최소 한 글자 이상의 이름으로 설정해주세요"))
            return
        }

        viewModelScope.launch(ioDispatcher) {
            updateMemberInformationUseCase.updateMemberName(member, name)
        }
        setMemberState(UiState.Success(Unit))
    }
}