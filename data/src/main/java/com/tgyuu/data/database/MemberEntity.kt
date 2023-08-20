package com.tgyuu.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member")
data class MemberEntity (
    val name : String,
    val image : String,
    val position : String,
    @PrimaryKey(autoGenerate = false) val number : Int,
    val isBenchWarmer : Boolean? = false
)