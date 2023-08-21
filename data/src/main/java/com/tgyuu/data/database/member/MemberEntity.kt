package com.tgyuu.data.database.member

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tgyuu.domain.entity.Member

@Entity(tableName = "member")
data class MemberEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val image: String,
    val position: String,
    val number: Int,
    val isBenchWarmer: Boolean? = false
)

fun MemberEntity.toMember() = Member(
    id = id,
    name = name,
    image = image,
    position = position,
    number = number,
    isBenchWarmer = isBenchWarmer
)