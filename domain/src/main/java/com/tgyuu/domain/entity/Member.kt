package com.tgyuu.domain.entity

data class Member(
    val name : String,
    val image : String,
    val position : String,
    val number : Int,
    val isBenchWarmer : Boolean? = false
)
