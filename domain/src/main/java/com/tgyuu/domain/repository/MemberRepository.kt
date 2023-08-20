package com.tgyuu.domain.repository

import com.tgyuu.domain.entity.Member

interface MemberRepository {
    suspend fun addNewMember(member: Member)
}