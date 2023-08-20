package com.tgyuu.presentation.feature.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.usecase.GetMemberUseCase
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
    @IO private val IOdispatcher: CoroutineDispatcher
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
        viewModelScope.launch(IOdispatcher) {
            getMemberUseCase().collect { setMemberListState(UiState.Success(it)) }
        }
    }

}