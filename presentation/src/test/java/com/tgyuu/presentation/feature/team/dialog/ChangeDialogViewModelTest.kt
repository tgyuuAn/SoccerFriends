package com.tgyuu.presentation.feature.team.dialog

import com.google.common.truth.Truth.assertThat
import com.tgyuu.domain.usecase.ValidateTeamNameUseCase
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.rule.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChangeDialogViewModelTest{

    @get:Rule
    @ExperimentalCoroutinesApi
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: ChangeDialogViewModel
    val testDispatcher = UnconfinedTestDispatcher()
    val validateTeamNameUseCase = ValidateTeamNameUseCase()

    @Before
    fun setUp(){
        viewModel = ChangeDialogViewModel(testDispatcher,validateTeamNameUseCase)
    }

    @Test
    fun `새로운 팀 이름은 1글자 이상이어야 한다`(){
        //given
        val wrongTeamName = ""

        //when
        viewModel.changeTeamName(wrongTeamName)

        //then
        val actual = viewModel.teamName.value
        val expected = UiState.Error("")
        assertThat(actual).isEqualTo(expected)
    }
}