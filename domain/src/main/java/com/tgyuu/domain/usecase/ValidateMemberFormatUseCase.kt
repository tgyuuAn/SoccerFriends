package com.tgyuu.domain.usecase

import java.lang.NumberFormatException
import javax.inject.Inject

class ValidateMemberFormatUseCase @Inject constructor() {

    operator fun invoke(
        newMemberName: String,
        newMemberBackNumber: String,
        newMemberPosition: String
    ): Boolean {
        if (newMemberName.isEmpty()) return false
        if (validateMemberNumber(newMemberBackNumber)) return false
        if (newMemberPosition.isEmpty()) return false
        return true
    }

    fun validateMemberName(name: String): Boolean {
        if (name.isEmpty()) return false
        return true
    }

    fun validateMemberPosition(position: String): Boolean {
        if (position.isEmpty()) return false
        return true
    }

    fun validateMemberNumber(doubtNumber: String): Boolean {
        if (!isNumeric(doubtNumber)) return false
        if (doubtNumber.length >=3) return false
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