package com.tgyuu.presentation.feature.team.dialog

import com.google.common.truth.Truth.assertThat
import com.tgyuu.domain.usecase.UpdateTeamInformationUseCase
import com.tgyuu.domain.usecase.ValidateTeamNameUseCase
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.rule.MainCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChangeDialogViewModelTest {

    @get:Rule
    @ExperimentalCoroutinesApi
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: ChangeDialogViewModel
    val testDispatcher = UnconfinedTestDispatcher()
    val validateTeamNameUseCase = ValidateTeamNameUseCase()
    val updateTeamInformationUsecase = mockk<UpdateTeamInformationUseCase>()

    @Before
    fun setUp() {
        viewModel =
            ChangeDialogViewModel(validateTeamNameUseCase, updateTeamInformationUsecase, testDispatcher)
    }

    @Test
    fun `새로운 팀 이름은 빈 칸일 수 없다`() {
        //given
        val wrongTeamName = ""

        //when
        viewModel.changeTeamName(wrongTeamName)

        //then
        val actual = viewModel.team.value
        val expected = UiState.Error("")
        assertThat(actual).isEqualTo(expected)
    }
}