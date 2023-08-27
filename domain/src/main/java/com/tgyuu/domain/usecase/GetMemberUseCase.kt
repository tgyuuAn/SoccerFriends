package com.tgyuu.domain.usecase

import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMemberUseCase @Inject constructor(private val memberReposiory: MemberRepository) {
    suspend fun getAllMembers(): Flow<List<Member>> {
        return memberReposiory.getAllMembers()
    }

    suspend fun getMemberById(id: Int): Flow<Member> {
        return memberReposiory.getMemberById(id)
    }

    suspend fun getAllSelectionMembers(): Flow<List<Member>> {
        return memberReposiory.getAllMembers().map {
            it.filter { member ->
                member.isBenchWarmer == false
            }
        }
    }

    suspend fun getAllReserveMembers(): Flow<List<Member>> {
        return memberReposiory.getAllMembers().map {
            it.filter { member ->
                member.isBenchWarmer == true
            }
        }
    }
}