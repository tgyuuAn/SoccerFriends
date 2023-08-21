package com.tgyuu.domain.usecase

import android.util.Log
import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.repository.TeamRepository
import javax.inject.Inject

class UpdateTeamInformationUseCase @Inject constructor(private val teamRepository: TeamRepository) {
    suspend fun updateTeamImage(team : Team, teamImage: String) {
        Log.d("test","useCase")
        teamRepository.changeTeamImage(team,teamImage)
    }

    suspend fun updateTeamName(team : Team, teamName: String) {
        teamRepository.changeTeamName(team,teamName)
    }
}