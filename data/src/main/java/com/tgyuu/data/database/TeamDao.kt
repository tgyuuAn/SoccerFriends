package com.tgyuu.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Update
    suspend fun updateTeam(teamEntity : TeamEntity)

    @Query("SELECT * FROM team")
    fun getTeam() : Flow<TeamEntity>

    /**
    @Query("SELECT memberList FROM team")
    fun getMemberList() : Flow<List<MemberEntity>>
    */
}