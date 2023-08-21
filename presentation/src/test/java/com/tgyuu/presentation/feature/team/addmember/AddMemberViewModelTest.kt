package com.tgyuu.presentation.feature.team.addmember

import com.google.common.truth.Truth.assertThat
import com.tgyuu.domain.usecase.AddMemberUseCase
import com.tgyuu.domain.usecase.ValidateNewMemberUseCase
import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.rule.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class AddMemberViewModelTest {

    @get:Rule
    @ExperimentalCoroutinesApi
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: AddMemberViewModel

    val addMemberUseCase = mockk<AddMemberUseCase>()
    val validateNewMemberUsecase = ValidateNewMemberUseCase()
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {

        coEvery {
            addMemberUseCase.invoke(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns Unit

        viewModel =
            AddMemberViewModel(
                validateNewMemberUsecase,
                addMemberUseCase,
                testDispatcher
            )
    }

    @Test
    fun `선수이름은 빈 칸일 수 없다`() = runTest {
        //given
        val wrongName = ""

        //when
        viewModel.addNewMember(
            newMemberName = wrongName,
            newMemberBackNumber = "1",
            newMemberPosition = "GK",
            isBenchWarmer = false,
            newMemberImage = ""
        )

        //then
        val actual = viewModel.addMemberState.value
        val expected = UiState.Error("이름은 최소 한 글자, 등 번호는 숫자, 포지션은 공백일 수 없습니다.")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `등 번호칸에는 문자를 넣을 수 없다`() = runTest {
        val wrongBackNumber = "Tgyuu"

        //when
        viewModel.addNewMember(
            newMemberName = "Tgyuu",
            newMemberBackNumber = wrongBackNumber,
            newMemberPosition = "GK",
            isBenchWarmer = false,
            newMemberImage = ""
        )

        //then
        val actual = viewModel.addMemberState.value
        val expected = UiState.Error("이름은 최소 한 글자, 등 번호는 숫자, 포지션은 공백일 수 없습니다.")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `포지션은 빈 칸일 수 없다`() {
        val wrongPosition = ""

        //when
        viewModel.addNewMember(
            newMemberName = "Tgyuu",
            newMemberBackNumber = "1",
            newMemberPosition = wrongPosition,
            isBenchWarmer = false,
            newMemberImage = ""
        )

        //then
        val actual = viewModel.addMemberState.value
        val expected = UiState.Error("이름은 최소 한 글자, 등 번호는 숫자, 포지션은 공백일 수 없습니다.")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `위 사항을 다 지켰을 경우, 새로운 선수 등록에 성공한다`() = runTest {
        val newMemberName = "Tgyuu"
        val newMemberBackNumber = "1"
        val newMemberPosition = "GK"

        //when
        viewModel.addNewMember(
            newMemberName = newMemberName,
            newMemberBackNumber = newMemberBackNumber,
            newMemberPosition = newMemberPosition,
            isBenchWarmer = false,
            newMemberImage = ""
        )

        //then
        val actual = viewModel.addMemberState.value
        val expected = UiState.Success(Unit)
        assertThat(actual).isEqualTo(expected)
    }
}