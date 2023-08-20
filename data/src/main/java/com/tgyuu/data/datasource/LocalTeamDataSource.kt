package com.tgyuu.data.datasource

import com.tgyuu.data.database.TeamDao
import com.tgyuu.data.database.TeamEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalTeamDataSource @Inject constructor(private val teamDao: TeamDao) {
    suspend fun updateTeam(team: TeamEntity) {
        teamDao.updateTeam(team)
    }

    fun getTeam() = flow {
        teamDao.getTeam().collect {
            emit(it)
        }
    }
}