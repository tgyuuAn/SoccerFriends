package com.tgyuu.domain.entity

data class Team (
    val name : String,
    val image : String? = null,
    val member : List<Member>
)