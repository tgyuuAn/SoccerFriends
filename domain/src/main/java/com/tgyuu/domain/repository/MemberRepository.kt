package com.tgyuu.domain.repository

import com.tgyuu.domain.entity.Member
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun addNewMember(member: Member)

    fun getAllMembers(): Flow<List<Member>>
}