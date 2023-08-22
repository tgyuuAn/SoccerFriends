package com.tgyuu.presentation.feature.scoreboard

import com.tgyuu.presentation.rule.MainCoroutineRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ScoreBoardViewModelTest{

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var scoreBoardViewModel: ScoreBoardViewModel

    @Before
    fun setUp(){
        scoreBoardViewModel = ScoreBoardViewModel()
    }

    @Test
    fun `플레이 타임의 기본 값은 0이다`(){
        //given


        //when


        //then

    }

    @Test
    fun `플레이 타임의 +버튼을 누르면 시간이 1분 증가한다`(){
        //given


        //when


        //then

    }

    @Test
    fun `플레이 타임의 -버튼을 누르면 시간이 1분 감소한다`(){
        //given


        //when


        //then

    }

    @Test
    fun `플레이 타임 설정이 0분일 때 -를 눌러도 감소하지 않는다`(){
        //given


        //when


        //then

    }

    @Test
    fun `경기 종료 전 알림의 기본 값은 0이다`(){
        //given


        //when


        //then

    }

    @Test
    fun `경기 종료 전 알림의 +버튼을 누르면 시간이 1분 증가한다`(){
        //given


        //when


        //then

    }

    @Test
    fun `경기 종료 전 알림의 -버튼을 누르면 시간이 1분 감소한다`(){
        //given


        //when


        //then

    }

    @Test
    fun `경기 종료 전 알림이 0분일 때 -를 눌러도 감소하지 않는다`(){
        //given


        //when


        //then

    }
}