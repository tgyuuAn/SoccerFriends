package com.tgyuu.data.database.member

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tgyuu.domain.entity.Member

@Entity(tableName = "member")
data class MemberEntity(
    val name: String,
    val image: String,
    val position: String,
    @PrimaryKey(autoGenerate = false) val number: Int,
    val isBenchWarmer: Boolean? = false
)

fun MemberEntity.toMember() = Member(name, image, position, number, isBenchWarmer)