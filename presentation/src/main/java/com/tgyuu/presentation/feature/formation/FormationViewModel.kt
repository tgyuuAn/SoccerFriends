package com.tgyuu.presentation.feature.formation

import androidx.lifecycle.ViewModel
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.usecase.GetMemberUseCase
import com.tgyuu.presentation.common.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FormationViewModel @Inject constructor(private val getMemberUseCase: GetMemberUseCase) :
    ViewModel() {

    sealed class FormationEvent {
    }

    private val _eventFlow = MutableSharedFlow<FormationEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _selectionMemberList = MutableStateFlow<UiState<List<Member>>>(UiState.Init)
    val selectionMemberList = _selectionMemberList.asStateFlow()

    private val _reserveMemberList = MutableStateFlow<UiState<List<Member>>>(UiState.Init)
    val reserveMemberList = _reserveMemberList.asStateFlow()

}