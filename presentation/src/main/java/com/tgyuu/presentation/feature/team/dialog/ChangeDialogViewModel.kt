package com.tgyuu.presentation.feature.team.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgyuu.domain.usecase.ValidateTeamNameUseCase
import com.tgyuu.presentation.common.di.IO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeDialogViewModel @Inject constructor(
    @IO private val IODispatcher: CoroutineDispatcher,
    private val validateTeamNameUseCase: ValidateTeamNameUseCase
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<DialogEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun event(event: DialogEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun cancel() = event(DialogEvent.Cancel)

    fun clickComplete() = event(DialogEvent.ClickComplete)

    fun changeTeamName(teamName: String) {
        viewModelScope.launch(IODispatcher) {

        }
    }

    sealed class DialogEvent {
        object Cancel : DialogEvent()
        object ClickComplete : DialogEvent()
    }

}