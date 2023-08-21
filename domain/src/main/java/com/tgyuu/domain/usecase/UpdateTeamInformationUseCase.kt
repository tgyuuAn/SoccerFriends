package com.tgyuu.domain.usecase

import com.tgyuu.domain.repository.TeamRepository
import javax.inject.Inject

class UpdateTeamInformationUseCase @Inject constructor(private val teamRepository: TeamRepository) {
    suspend fun updateTeamImage(teamImage: String) {
        teamRepository.changeTeamImage(teamImage)
    }

    suspend fun updateTeamName(teamName: String) {
        teamRepository.changeTeamName(teamName)
    }
}