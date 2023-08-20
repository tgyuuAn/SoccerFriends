package com.tgyuu.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member : MemberEntity)

    @Delete
    suspend fun deleteMember(member : MemberEntity)

    @Query("SELECT * FROM member")
    fun getAllMembers() : Flow<List<MemberEntity>>
}