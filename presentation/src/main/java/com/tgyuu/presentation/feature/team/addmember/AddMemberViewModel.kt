package com.tgyuu.presentation.feature.team.addmember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.usecase.AddMemberUseCase
import com.tgyuu.domain.usecase.ValidateNewMemberUseCase
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
class AddMemberViewModel @Inject constructor(
    private val validateNewMemberUseCase: ValidateNewMemberUseCase,
    private val addMemberUseCase: AddMemberUseCase,
    @IO private val IOdispatcher: CoroutineDispatcher
) :
    ViewModel() {
    private val _eventFlow = MutableSharedFlow<AddMemberEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: AddMemberEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private val _addMemberState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val addMemberState = _addMemberState.asStateFlow()

    private fun setAddMemberState(uiState: UiState<Unit>) {
        _addMemberState.value = uiState
    }

    fun addNewMember(
        newMemberName: String,
        newMemberBackNumber: String,
        newMemberPosition: String,
        isBenchWarmer: Boolean
    ) {
        setAddMemberState(UiState.Loading)

        if (!validateNewMemberUseCase(newMemberName, newMemberBackNumber, newMemberPosition)) {
            setAddMemberState(UiState.Error("이름은 최소 한 글자, 등 번호는 숫자, 포지션은 공백일 수 없습니다."))
            return
        }

        viewModelScope.launch(IOdispatcher) {
            addMemberUseCase(
                newMemberName,
                newMemberBackNumber.toInt(),
                newMemberPosition,
                isBenchWarmer
            )
        }
    }

    fun clickComplete() = event(AddMemberEvent.ClickComplete)

    fun clickReset() = event(AddMemberEvent.ClickReset)

    sealed class AddMemberEvent {
        object ClickComplete : AddMemberEvent()
        object ClickReset : AddMemberEvent()
    }
}