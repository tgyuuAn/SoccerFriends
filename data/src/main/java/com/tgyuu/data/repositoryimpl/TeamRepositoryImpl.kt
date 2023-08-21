package com.tgyuu.data.repositoryimpl

import com.tgyuu.data.database.team.TeamEntity
import com.tgyuu.data.database.team.toTeam
import com.tgyuu.data.datasource.LocalTeamDataSource
import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(private val localTeamDataSource: LocalTeamDataSource) :
    TeamRepository {
    override suspend fun changeTeamName(teamName: String) {

        lateinit var newTeamEntity: TeamEntity

        getTeam().collect {
            newTeamEntity = TeamEntity(name = teamName, image = it.image)
        }
        localTeamDataSource.updateTeam(newTeamEntity)
    }

    override suspend fun changeTeamImage(imageUri: String) {
        lateinit var newTeamEntity: TeamEntity

        getTeam().collect {
            newTeamEntity = TeamEntity(name = it.name, image = imageUri)
        }
        localTeamDataSource.updateTeam(newTeamEntity)
    }

    override suspend fun getTeam(): Flow<Team> = flow {
        localTeamDataSource.getTeam().collect {
            if(it != null) emit(it.toTeam())
        }
    }
}