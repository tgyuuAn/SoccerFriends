package com.tgyuu.soccerfriends.feature.team.addmember

import com.google.common.truth.Truth.assertThat
import com.tgyuu.soccerfriends.rule.MainCoroutineRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class AddMemberViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: AddMemberViewModel

    @Before
    fun setUp() {
        viewModel = AddMemberViewModel()
    }

    @Test
    fun `선수이름은 최소 1글자 이상이어야 한다`() {
        //given
        val newMemberName = ""

        //when
        viewModel.complete(
            newMemberName = newMemberName,
            newMemberBackNumber = "1",
            newMemberPosition = "GK",
            isBenchWarmer = false
        )

        //then
        val expected = addMemberState.Error
        assertThat(viewModel.addMemberState).isEuqalTo(expeced)
    }

    @Test
    fun `등 번호칸에는 숫자만 넣을 수 있다`() {
        val newMemberBackNumber = "Tgyuu"

        //when
        viewModel.complete(
            newMemberName = "Tgyuu",
            newMemberBackNumber = newMemberBackNumber,
            newMemberPosition = "GK",
            isBenchWarmer = false
        )

        //then
        val expected = addMemberState.Error
        assertThat(viewModel.addMemberState).isEuqalTo(expeced)
    }

    @Test
    fun `포지션은 빈 칸일 수 없다`() {
        val newMemberPosition = ""

        //when
        viewModel.complete(
            newMemberName = "Tgyuu",
            newMemberBackNumber = 1,
            newMemberPosition = newMemberPosition,
            isBenchWarmer = false
        )

        //then
        val expected = addMemberState.Error
        assertThat(viewModel.addMemberState).isEuqalTo(expeced)
    }

    @Test
    fun `위 사항을 다 지켰을 경우, 새로운 선수 등록에 성공한다`(){
        val newMemberName = "Tgyuu"
        val newMemberBackNumber = "1"
        val newMemberPosition = "GK"

        //when
        viewModel.complete(
            newMemberName = newMemberName,
            newMemberBackNumber = newMemberBackNumber,
            newMemberPosition = newMemberPosition,
            isBenchWarmer = false
        )

        //then
        val expected = addMemberState.Success
        assertThat(viewModel.addMemberState).isEuqalTo(expeced)
    }
}