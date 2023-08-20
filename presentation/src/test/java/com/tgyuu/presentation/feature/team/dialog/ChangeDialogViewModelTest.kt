package com.tgyuu.presentation.feature.team.dialog

import com.tgyuu.presentation.common.base.UiState
import com.tgyuu.presentation.feature.team.recyclerview.AdapterViewModel
import com.tgyuu.presentation.rule.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChangeDialogViewModelTest{

    @get:Rule
    @ExperimentalCoroutinesApi
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: AdapterViewModel
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp(){
        viewModel = AdapterViewModel()
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