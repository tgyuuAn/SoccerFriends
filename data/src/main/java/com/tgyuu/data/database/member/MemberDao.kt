package com.tgyuu.data.database.member

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member : MemberEntity)

    @Delete
    suspend fun deleteMember(member : MemberEntity)

    @Update
    suspend fun updateMember(member : MemberEntity)

    @Query("SELECT * FROM member")
    fun getAllMembers() : Flow<List<MemberEntity>>

    @Query("SELECT * FROM member WHERE id = :id")
    fun getMemberById(id: Int) : Flow<MemberEntity>
}