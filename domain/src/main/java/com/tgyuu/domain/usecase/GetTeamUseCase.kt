package com.tgyuu.domain.usecase

import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamUseCase @Inject constructor(private val teamRepository: TeamRepository) {
    operator suspend fun invoke() : Flow<Team> {
        return teamRepository.getTeam()
    }
}