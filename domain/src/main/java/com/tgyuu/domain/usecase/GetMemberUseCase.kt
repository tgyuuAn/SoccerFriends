package com.tgyuu.domain.usecase

import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMemberUseCase @Inject constructor(private val memberReposiory: MemberRepository) {
    fun getAllMembers(): Flow<List<Member>> {
        return memberReposiory.getAllMembers().map{
            it.sortedWith(compareBy({it.isBenchWarmer==false},{it.number}))
        }
    }

    fun getMemberById(id: Int): Flow<Member> {
        return memberReposiory.getMemberById(id)
    }

    fun getAllSelectionMembers(): Flow<List<Member>> {
        return memberReposiory.getAllMembers().map {
            it.filter { member ->
                member.isBenchWarmer == false
            }.sortedWith(compareBy({it.isBenchWarmer==false},{it.number}))
        }
    }

    fun getAllReserveMembers(): Flow<List<Member>> {
        return memberReposiory.getAllMembers().map {
            it.filter { member ->
                member.isBenchWarmer == true
            }.sortedWith(compareBy({it.isBenchWarmer==false},{it.number}))
        }
    }
}