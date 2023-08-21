package com.tgyuu.domain.usecase

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class ValidateTeamFormatUseCaseTest {

    lateinit var validateTeamFormatUseCase: ValidateTeamFormatUseCase

    @Before
    fun setUp() {
        validateTeamFormatUseCase = ValidateTeamFormatUseCase()
    }

    @Test
    fun `새로운 팀 이름은 빈 칸일 수 없다`() {
        //given
        val wrongTeamName = ""

        //when


        //then
        val actual = validateTeamFormatUseCase(wrongTeamName)
        Truth.assertThat(actual).isFalse()
    }

    @Test
    fun `새로운 팀 이름은 한 글자 이상이면 된다`() {
        //given
        val newTeamName = "가"

        //when


        //then
        val actual = validateTeamFormatUseCase(newTeamName)
        Truth.assertThat(actual).isTrue()
    }
}