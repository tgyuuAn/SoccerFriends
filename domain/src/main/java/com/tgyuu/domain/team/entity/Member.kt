package com.tgyuu.domain.team.entity

data class Member(
    val name : String,
    val image : String,
    val position : String,
    val number : Int,
    val isSelection : Boolean? = true
)
