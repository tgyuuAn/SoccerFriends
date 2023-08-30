package com.tgyuu.data.datasource

import com.tgyuu.data.database.team.TeamDao
import com.tgyuu.data.database.team.TeamEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTeamDataSource @Inject constructor(private val teamDao: TeamDao) {
    suspend fun updateTeam(team: TeamEntity) {
        return teamDao.updateTeam(team)
    }

    fun getTeam() : Flow<TeamEntity> {
        return teamDao.getTeam()
    }

    fun createNewTeam() {
        return teamDao.insertTeam(TeamEntity(name="팀 명", image=""))
    }
}