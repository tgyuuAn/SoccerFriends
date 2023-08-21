package com.tgyuu.domain.repository

import com.tgyuu.domain.entity.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    suspend fun changeTeamName(team : Team, teamName : String)

    suspend fun changeTeamImage(team : Team, imageUri : String)

    suspend fun getTeam(): Flow<Team>
}