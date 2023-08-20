package com.tgyuu.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MemberEntity::class, TeamEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SoccerFriendsDatabase : RoomDatabase() {
    abstract fun getMemberDao(): MemberDao
    abstract fun getTeamDao(): TeamDao
}