package com.tgyuu.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MemberEntity::class],
    version = 1
)
abstract class MemberDatabase : RoomDatabase(){
    abstract fun getMemberDao() : MemberDao
}