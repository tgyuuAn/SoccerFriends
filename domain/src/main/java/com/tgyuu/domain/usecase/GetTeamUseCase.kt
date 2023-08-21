package com.tgyuu.domain.usecase

import com.tgyuu.domain.repository.TeamRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTeamUseCase @Inject constructor(private val teamRepository: TeamRepository) {
    operator suspend fun invoke() = flow {
        teamRepository.getTeam().collect {
            emit(it)
        }
    }
}