package com.tgyuu.domain.usecase

import android.util.Log
import com.tgyuu.domain.repository.TeamRepository
import javax.inject.Inject

class ChangeTeamNameUseCase @Inject constructor(private val teamRepository: TeamRepository) {
    operator suspend fun invoke(teamName: String) {
        teamRepository.changeTeamName(teamName)
    }
}