package com.tgyuu.data.repositoryimpl

import com.tgyuu.data.database.TeamEntity
import com.tgyuu.data.database.toTeam
import com.tgyuu.data.datasource.LocalTeamDataSource
import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(private val localTeamDataSource: LocalTeamDataSource) :
    TeamRepository {
    override suspend fun changeTeamName(teamName: String) {
        val teamEntity = TeamEntity(name = teamName)
        localTeamDataSource.updateTeam(teamEntity)
    }

    override suspend fun getTeam(): Flow<Team> = flow{
        localTeamDataSource.getTeam().collect {
            emit(it.toTeam())
        }
    }
}