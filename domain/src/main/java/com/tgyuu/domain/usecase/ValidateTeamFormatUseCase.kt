package com.tgyuu.domain.usecase

import javax.inject.Inject

class ValidateTeamFormatUseCase @Inject constructor() {
    operator fun invoke(
        newTeamName: String
    ): Boolean {
        if (newTeamName.length == 0) return false
        return true
    }
}