package com.tgyuu.domain.team.usecase

import com.google.common.truth.Truth.assertThat
import com.tgyuu.domain.usecase.ValidateNewMemberUseCase
import org.junit.Test


class ValidateNewMemberUseCaseTest {

    val validateNewMemberUseCase = ValidateNewMemberUseCase()

    @Test
    fun `등 번호는 문자일 수 없다`() {
        //given
        val wrongBackNumber = "Tgyuu"

        //when


        //then
        assertThat(validateNewMemberUseCase.isNumeric(wrongBackNumber)).isFalse()
    }
}