package com.tgyuu.soccerfriends.feature.team.addmember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.team.entity.Member
import com.tgyuu.domain.team.usecase.ValidateNewMemberUseCase
import com.tgyuu.soccerfriends.common.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMemberViewModel @Inject constructor(private val validateNewMemberUseCase : ValidateNewMemberUseCase) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<AddMemberEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: AddMemberEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private val _addMemberState = MutableStateFlow<UiState<Member>>(UiState.Loading)
    val addMemberState = _addMemberState.asStateFlow()

    private fun setAddMemberState(uiState: UiState<Member>) {
        _addMemberState.value = uiState
    }

    fun complete(
        newMemberName: String,
        newMemberBackNumber: String,
        newMemberPosition: String,
        isBenchWarmer: Boolean
    ) {
        setAddMemberState(UiState.Loading)

        validateNewMemberUseCase(newMemberName,newMemberBackNumber,newMemberPosition)

        viewModelScope.launch(Dispatchers.IO) {
            AddNewMemberUseCase().collect {

            }
        }
    }

    fun clickComplete() = event(AddMemberEvent.ClickComplete)

    fun clickReset() = event(AddMemberEvent.ClickReset)

    sealed class AddMemberEvent {
        object ClickComplete : AddMemberEvent()
        object ClickReset : AddMemberEvent()
    }
}