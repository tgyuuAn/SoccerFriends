package com.tgyuu.domain.usecase

import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.repository.TeamRepository
import javax.inject.Inject

class UpdateTeamInformationUseCase @Inject constructor(private val teamRepository: TeamRepository) {
    suspend fun updateTeamImage(team : Team, teamImage: String) {
        return teamRepository.changeTeamImage(team,teamImage)
    }

    suspend fun updateTeamName(team : Team, teamName: String) {
        return teamRepository.changeTeamName(team,teamName)
    }
}