package com.tgyuu.data.database

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tgyuu.domain.entity.Team

@Entity(tableName = "team")
data class TeamEntity(
    @PrimaryKey(autoGenerate = false) val name: String,
    val image: String? = null
)


fun TeamEntity.toTeam() = Team(name = name, image = "", member = listOf())
