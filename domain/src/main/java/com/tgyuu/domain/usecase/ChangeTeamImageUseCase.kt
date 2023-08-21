package com.tgyuu.domain.usecase

import com.tgyuu.domain.repository.TeamRepository
import javax.inject.Inject

class ChangeTeamImageUseCase @Inject constructor(private val teamRepository: TeamRepository) {
    operator suspend fun invoke(teamImage: String) {
        teamRepository.changeTeamImage(teamImage)
    }
}