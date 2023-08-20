package com.tgyuu.domain.team.repository

import javax.inject.Inject

interface MemberReposiory {
    suspend fun addNewMember(member: Member): Result<Unit>
}