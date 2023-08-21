package com.tgyuu.presentation.feature.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.usecase.UpdateTeamInformationUseCase
import com.tgyuu.domain.usecase.GetMemberUseCase
import com.tgyuu.domain.usecase.GetTeamUseCase
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
class TeamViewModel @Inject constructor(
    private val getMemberUseCase: GetMemberUseCase,
    private val updateMemberInformationUseCase: UpdateMemberInformationUseCase,
    private val updateTeamInformationUseCase: UpdateTeamInformationUseCase,
    private val getTeamUseCase: GetTeamUseCase,
    @IO private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<TeamEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: TeamEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun addMember() = event(TeamEvent.AddMember)

    fun changeTeamName() = event(TeamEvent.ChangeTeamName)

    fun changeTeamImage() = event(TeamEvent.ChangeTeamImage)

    sealed class TeamEvent {
        object AddMember : TeamEvent()
        object ChangeTeamName : TeamEvent()
        object ChangeTeamImage : TeamEvent()
    }

    private val _memberListFlow = MutableStateFlow<UiState<List<Member>>>(UiState.Loading)
    val memberListFlow = _memberListFlow.asStateFlow()

    private fun setMemberListState(uiState: UiState<List<Member>>) {
        _memberListFlow.value = uiState
    }

    fun getMemberList() {
        setMemberListState(UiState.Loading)
        viewModelScope.launch(ioDispatcher) {
            getMemberUseCase().collect { setMemberListState(UiState.Success(it)) }
        }
    }

    fun updateMemberImage(member: Member, image: String) {
        setMemberListState(UiState.Loading)
        viewModelScope.launch(ioDispatcher) {
            updateMemberInformationUseCase.updateMemberImage(member, image)
            getMemberUseCase().collect { setMemberListState(UiState.Success(it)) }
        }
    }

    private val _team = MutableStateFlow<UiState<Team>>(UiState.Loading)
    val team = _team.asStateFlow()

    private fun setTeamState(uiState: UiState<Team>) {
        _team.value = uiState
    }

    fun updateTeamImage(teamImage: String) {
        setTeamState(UiState.Loading)
        viewModelScope.launch(ioDispatcher) {
            updateTeamInformationUseCase.updateTeamImage(teamImage)
        }
        getTeam()
    }

    fun getTeam() {
        setTeamState(UiState.Loading)
        viewModelScope.launch(ioDispatcher) {
            getTeamUseCase().collect {
                setTeamState(UiState.Success(it))
            }
        }
    }
}