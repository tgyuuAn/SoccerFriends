package com.tgyuu.domain.team.usecase

import android.util.Log
import java.lang.NumberFormatException
import javax.inject.Inject

class ValidateNewMemberUseCase @Inject constructor() {

    operator fun invoke(
        newMemberName: String,
        newMemberBackNumber: String,
        newMemberPosition: String
    ): Boolean {
        if (newMemberName.length == 0) return false
        if (!isNumeric(newMemberBackNumber)) return false
        if (newMemberPosition.length == 0) return false
        return true
    }

    fun isNumeric(doubtNumber: String): Boolean {
        return try {
            doubtNumber.toInt()
            true
        } catch (ex: NumberFormatException) {
            false
        }
    }
}