package com.tgyuu.presentation.feature.scoreboard

import com.google.common.truth.Truth.assertThat
import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.usecase.GetTeamUseCase
import com.tgyuu.presentation.rule.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ScoreBoardViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var scoreBoardViewModel: ScoreBoardViewModel
    val testDispatcher = UnconfinedTestDispatcher()
    val getTeamUseCase = mockk<GetTeamUseCase>()

    @Before
    fun setUp() {

        coEvery {
            getTeamUseCase.invoke()
        } returns flow {
            emit(Team("팀 명","",1))
        }

        scoreBoardViewModel = ScoreBoardViewModel(getTeamUseCase,testDispatcher)
    }

    @Test
    fun `플레이 타임의 기본 값은 0이다`() {
        //given


        //when


        //then
        val expected = 0
        val actual = scoreBoardViewModel.playTime.value
        assertThat(actual).isEqualTo(expected)

    }

    @Test
    fun `플레이 타임의 +버튼을 누르면 시간이 1분 증가한다`() {
        //given

        //when
        scoreBoardViewModel.clickPlusPlayTime()

        //then
        val expected = 1
        val actual = scoreBoardViewModel.playTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `플레이 타임의 -버튼을 누르면 시간이 1분 감소한다`() {
        //given - playTime의 값을 1로 설정한다
        scoreBoardViewModel.clickPlusPlayTime()

        //when
        scoreBoardViewModel.clickMinusPlayTime()

        //then
        val expected = 0
        val actual = scoreBoardViewModel.playTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `플레이 타임 설정이 0분일 때 -를 눌러도 감소하지 않는다`() {
        //given


        //when
        scoreBoardViewModel.clickMinusPlayTime()

        //then
        val expected = 0
        val actual = scoreBoardViewModel.playTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `플레이 타임의 설정이 99분일 때 +를 눌러도 증가하지 않는다`() {
        //given - playTime의 값을 99로 설정한다
        repeat(99) {
            scoreBoardViewModel.clickPlusPlayTime()
        }

        //when
        scoreBoardViewModel.clickPlusPlayTime()

        //then
        val expected = 99
        val actual = scoreBoardViewModel.playTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `경기 종료 전 알림의 기본 값은 0이다`() {
        //given


        //when


        //then
        val expected = 0
        val actual = scoreBoardViewModel.alarmTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `경기 종료 전 알림의 +버튼을 누르면 시간이 1분 증가한다`() {
        //given
        scoreBoardViewModel.clickPlusPlayTime()

        //when
        scoreBoardViewModel.clickPlusAlarmTime()

        //then
        val expected = 1
        val actual = scoreBoardViewModel.alarmTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `경기 종료 전 알림의 -버튼을 누르면 시간이 1분 감소한다`() {
        //given
        scoreBoardViewModel.clickPlusAlarmTime()

        //when
        scoreBoardViewModel.clickMinusAlarmTime()

        //then
        val expected = 0
        val actual = scoreBoardViewModel.alarmTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `경기 종료 전 알림이 0분일 때 -를 눌러도 감소하지 않는다`() {
        //given


        //when
        scoreBoardViewModel.clickMinusAlarmTime()

        //then
        val expected = 0
        val actual = scoreBoardViewModel.alarmTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `경기 종료 전 알림이 99분일 때 +를 눌러도 증가하지 않는다`() {
        //given - playTime의 값을 99로 설정한다
        repeat(99) {
            scoreBoardViewModel.clickPlusPlayTime()
            scoreBoardViewModel.clickPlusAlarmTime()
        }

        //when
        scoreBoardViewModel.clickPlusAlarmTime()

        //then
        val expected = 99
        val actual = scoreBoardViewModel.alarmTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `경기 종료 전 알림이 플레이 타임과 같을 경우 증가되지 않는다`(){
        //given

        //when
        scoreBoardViewModel.clickPlusAlarmTime()

        //then
        val expected = 0
        val actual = scoreBoardViewModel.alarmTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `플레이 타임이 경기 종료 전 알림과 같을 경우 감소되지 않는다`(){
        //given
        scoreBoardViewModel.clickPlusPlayTime()
        scoreBoardViewModel.clickPlusAlarmTime()

        //when
        scoreBoardViewModel.clickMinusPlayTime()

        //then
        val expected = 1
        val actual = scoreBoardViewModel.alarmTime.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `홈 팀의 기본 점수는 0이다`(){
        //given

        //when
        scoreBoardViewModel.clickPlusAlarmTime()

        //then
        val expected = 0
        val actual = scoreBoardViewModel.homeTeamScore.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `홈 팀의 점수가 0일 때 -를 눌러도 감소하지 않는다`(){
        //given


        //when
        scoreBoardViewModel.clickMinusHomeTeamScore()

        //then
        val expected = 0
        val actual = scoreBoardViewModel.homeTeamScore.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `홈 팀의 점수가 99일 때 +를 눌러도 증가하지 않는다`(){
        //given - playTime의 값을 99로 설정한다
        repeat(99){
            scoreBoardViewModel.clickPlusHomeTeamScore()
        }

        //when
        scoreBoardViewModel.clickPlusHomeTeamScore()

        //then
        val expected = 99
        val actual = scoreBoardViewModel.homeTeamScore.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `어웨이 팀의 기본 점수는 0이다`() {
        //given


        //when


        //then
        val expected = 0
        val actual = scoreBoardViewModel.awayTeamScore.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `어웨이 팀의 점수가 0일 때 -를 눌러도 감소하지 않는다`(){
        //given


        //when
        scoreBoardViewModel.clickMinusAwayTeamScore()

        //then
        val expected = 0
        val actual = scoreBoardViewModel.awayTeamScore.value
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `어웨이 팀의 점수가 99일 때 +를 눌러도 증가하지 않는다`(){
        //given - playTime의 값을 99로 설정한다
        repeat(99){
            scoreBoardViewModel.clickPlusAwayTeamScore()
        }

        //when
        scoreBoardViewModel.clickPlusAwayTeamScore()

        //then
        val expected = 99
        val actual = scoreBoardViewModel.awayTeamScore.value
        assertThat(actual).isEqualTo(expected)
    }
}