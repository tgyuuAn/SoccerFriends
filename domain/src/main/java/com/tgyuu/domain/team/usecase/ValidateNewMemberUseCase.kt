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
        if (checkNumber(newMemberBackNumber)) return false
        if (newMemberPosition.length == 0) return false
        return true
    }

    private fun checkNumber(doubtNumber: String): Boolean {
        return try {
            doubtNumber.toDouble()
            true
        } catch (e: NumberFormatException) {
            Log.d("tgyuu", e.toString())
            false
        }
    }
}