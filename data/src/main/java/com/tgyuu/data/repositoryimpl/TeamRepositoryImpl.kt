package com.tgyuu.data.repositoryimpl

import com.tgyuu.data.database.team.TeamEntity
import com.tgyuu.data.database.team.toTeam
import com.tgyuu.data.datasource.LocalTeamDataSource
import com.tgyuu.domain.entity.Team
import com.tgyuu.domain.repository.TeamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(private val localTeamDataSource: LocalTeamDataSource) :
    TeamRepository {
    override suspend fun changeTeamName(team: Team, teamName: String) {
        val newTeamEntity = TeamEntity(name = teamName, image = team.image, id = team.id)
        localTeamDataSource.updateTeam(newTeamEntity)
    }

    override suspend fun changeTeamImage(team: Team, imageUri: String) {
        val newTeamEntity = TeamEntity(name = team.name, image = imageUri, id = team.id)
        localTeamDataSource.updateTeam(newTeamEntity)
    }

    override suspend fun getTeam() : Flow<Team> {
        localTeamDataSource.getTeam().map{
            if (it == null) {
                localTeamDataSource.createNewTeam()
                getTeam()
                return@map
            }
        }

        return localTeamDataSource.getTeam().map{
            it.toTeam()
        }
    }
}