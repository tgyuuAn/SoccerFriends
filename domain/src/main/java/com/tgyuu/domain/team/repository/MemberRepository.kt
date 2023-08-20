package com.tgyuu.domain.team.repository

import com.tgyuu.domain.team.entity.Member

interface MemberRepository {
    suspend fun addNewMember(member: Member)
}