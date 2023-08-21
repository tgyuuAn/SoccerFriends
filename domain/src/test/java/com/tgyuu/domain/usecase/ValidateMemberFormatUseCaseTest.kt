package com.tgyuu.domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class ValidateMemberFormatUseCaseTest {

    val validateMemberFormatUseCase = ValidateMemberFormatUseCase()

    @Test
    fun `등 번호는 문자일 수 없다`() {
        //given
        val wrongBackNumber = "Tgyuu"

        //when


        //then
        assertThat(validateMemberFormatUseCase.isNumeric(wrongBackNumber)).isFalse()
    }
}