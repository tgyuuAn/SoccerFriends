package com.tgyuu.domain.repository

import com.tgyuu.domain.entity.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    suspend fun changeTeamName(teamName : String)

    suspend fun changeTeamImage(imageUri : String)

    suspend fun getTeam(): Flow<Team>
}