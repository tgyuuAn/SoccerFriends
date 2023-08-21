package com.tgyuu.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tgyuu.data.database.member.MemberDao
import com.tgyuu.data.database.member.MemberEntity
import com.tgyuu.data.database.team.TeamDao
import com.tgyuu.data.database.team.TeamEntity
import com.tgyuu.data.di.DATABASE_NAME
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

@Database(
    entities = [MemberEntity::class, TeamEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SoccerFriendsDatabase : RoomDatabase() {
    abstract fun getMemberDao(): MemberDao
    abstract fun getTeamDao(): TeamDao

    companion object {
        fun getInstance(context: Context): SoccerFriendsDatabase = Room.databaseBuilder(
            context, SoccerFriendsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}