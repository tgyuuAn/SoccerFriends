package com.tgyuu.presentation.feature.team.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.usecase.UpdateTeamInformationUseCase
import com.tgyuu.domain.usecase.ValidateTeamNameUseCase
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
    private val validateTeamNameUseCase: ValidateTeamNameUseCase,
    private val updateTeamInformationUseCase: UpdateTeamInformationUseCase,
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

    private val _team = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val team = _team.asStateFlow()

    private fun setTeamNameState(uiState: UiState<Unit>) {
        _team.value = uiState
    }

    fun changeTeamName(teamName: String) {
        setTeamNameState(UiState.Loading)

        if (!validateTeamNameUseCase(teamName)) {
            setTeamNameState(UiState.Error(""))
            return
        }

        viewModelScope.launch(ioDispatcher) {
            updateTeamInformationUseCase.updateTeamName(teamName)
        }
        setTeamNameState(UiState.Success(Unit))
    }
}