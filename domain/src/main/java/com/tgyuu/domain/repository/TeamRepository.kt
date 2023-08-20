package com.tgyuu.domain.repository

interface TeamRepository {
    suspend fun changeTeamName(teamName : String)

    fun getTeamInformation(): Unit
}