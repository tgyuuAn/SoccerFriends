package com.tgyuu.data.repositoryimpl

import android.util.Log
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
    override suspend fun changeTeamName(team: Team, teamName: String) {
        val newTeamEntity = TeamEntity(name = teamName, image = team.image, id = team.id)
        localTeamDataSource.updateTeam(newTeamEntity)
    }

    override suspend fun changeTeamImage(team: Team, imageUri: String) {
        val newTeamEntity = TeamEntity(name = team.name, image = imageUri, id = team.id)
        localTeamDataSource.updateTeam(newTeamEntity)
    }

    override suspend fun getTeam(): Flow<Team> = flow {
        Log.d("tgyuu","getTeam() 호출")
        localTeamDataSource.getTeam().collect {
            if (it == null) {
                localTeamDataSource.createNewTeam()
                getTeam()
                return@collect
            }
            emit(it.toTeam())
        }
    }
}