package com.tgyuu.presentation.feature.team.dialog

import com.google.common.truth.Truth.assertThat
import com.tgyuu.domain.usecase.UpdateTeamInformationUseCase
import com.tgyuu.domain.usecase.ValidateTeamFormatUseCase
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
    val validateTeamFormatUseCase = ValidateTeamFormatUseCase()
    val updateTeamInformationUsecase = mockk<UpdateTeamInformationUseCase>()

    @Before
    fun setUp() {
        viewModel =
            ChangeDialogViewModel(validateTeamFormatUseCase, updateTeamInformationUsecase, testDispatcher)
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