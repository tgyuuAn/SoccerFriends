package com.tgyuu.presentation.feature.team.dialog

import com.google.common.truth.Truth.assertThat
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.usecase.UpdateMemberInformationUseCase
import com.tgyuu.domain.usecase.UpdateTeamInformationUseCase
import com.tgyuu.domain.usecase.ValidateMemberFormatUseCase
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
    private val updateMemberInformationUseCase = mockk<UpdateMemberInformationUseCase>()
    private val validateMemberFormatUseCase = ValidateMemberFormatUseCase()

    @Before
    fun setUp() {
        viewModel =
            ChangeDialogViewModel(
                validateTeamFormatUseCase,
                updateTeamInformationUsecase,
                updateMemberInformationUseCase,
                validateMemberFormatUseCase,
                testDispatcher
            )
    }

    @Test
    fun `새로운 팀 이름은 빈 칸일 수 없다`() {
        //given
        val wrongTeamName = ""

        //when
        viewModel.changeTeamName(wrongTeamName)

        //then
        val actual = viewModel.team.value
        val expected = UiState.Error("최소 한 글자 이상의 팀 명으로 설정해주세요")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `포지션은 빈 칸일 수 없다`() {
        val wrongPosition = ""
        val member = Member(
            name = "Tgyuu",
            number = 1,
            position = "GK",
            isBenchWarmer = false,
            image = "",
            id = 1
        )

        //when
        viewModel.updateMemberPosition(member, wrongPosition)

        //then
        val actual = viewModel.member.value
        val expected = UiState.Error("최소 한 글자 이상의 포지션으로 설정해주세요")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `선수 이름은 빈 칸일 수 없다`() {
        val wrongName = ""
        val member = Member(
            name = "Tgyuu",
            number = 1,
            position = "GK",
            isBenchWarmer = false,
            image = "",
            id = 1
        )

        //when
        viewModel.updateMemberName(member, wrongName)

        //then
        val actual = viewModel.member.value
        val expected = UiState.Error("최소 한 글자 이상의 이름으로 설정해주세요")
        assertThat(actual).isEqualTo(expected)
    }
}