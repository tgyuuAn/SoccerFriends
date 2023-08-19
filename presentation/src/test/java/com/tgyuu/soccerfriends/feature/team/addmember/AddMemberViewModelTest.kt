package com.tgyuu.soccerfriends.feature.team.addmember

import com.google.common.truth.Truth.assertThat
import com.tgyuu.domain.team.usecase.AddNewMemberUseCase
import com.tgyuu.domain.team.usecase.ValidateNewMemberUseCase
import com.tgyuu.soccerfriends.common.base.UiState
import com.tgyuu.soccerfriends.rule.MainCoroutineRule
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

    val addNewMemberUseCase = mockk<AddNewMemberUseCase>()
    val validateNewMemberUsecase = ValidateNewMemberUseCase()
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {

        coEvery {
            addNewMemberUseCase.invoke(
                any(),
                any(),
                any(),
                any()
            )
        } returns Result.success(Unit)

        viewModel =
            AddMemberViewModel(validateNewMemberUsecase, addNewMemberUseCase, testDispatcher)
    }

    @Test
    fun `선수이름은 최소 1글자 이상이어야 한다`() = runTest {
        //given
        val wrongName = ""

        //when
        viewModel.addNewMember(
            newMemberName = wrongName,
            newMemberBackNumber = "1",
            newMemberPosition = "GK",
            isBenchWarmer = false
        )

        //then
        val expected = UiState.Error("이름은 최소 한 글자, 등 번호는 숫자, 포지션은 공백일 수 없습니다.")
        assertThat(viewModel.addMemberState.value).isEqualTo(expected)
    }

    @Test
    fun `등 번호칸에는 문자를 넣을 수 없다`() = runTest {
        val wrongBackNumber = "Tgyuu"

        //when
        viewModel.addNewMember(
            newMemberName = "Tgyuu",
            newMemberBackNumber = wrongBackNumber,
            newMemberPosition = "GK",
            isBenchWarmer = false
        )

        //then
        val expected = UiState.Error("이름은 최소 한 글자, 등 번호는 숫자, 포지션은 공백일 수 없습니다.")
        assertThat(viewModel.addMemberState.value).isEqualTo(expected)
    }

    @Test
    fun `포지션은 빈 칸일 수 없다`() {
        val wrongPosition = ""

        //when
        viewModel.addNewMember(
            newMemberName = "Tgyuu",
            newMemberBackNumber = "1",
            newMemberPosition = wrongPosition,
            isBenchWarmer = false
        )

        //then
        val expected = UiState.Error("이름은 최소 한 글자, 등 번호는 숫자, 포지션은 공백일 수 없습니다.")
        assertThat(viewModel.addMemberState.value).isEqualTo(expected)
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
            isBenchWarmer = false
        )

        //then
        val expected = UiState.Success(Unit)
        assertThat(viewModel.addMemberState.value).isEqualTo(expected)
    }
}