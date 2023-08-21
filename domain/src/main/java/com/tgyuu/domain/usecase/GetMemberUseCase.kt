package com.tgyuu.domain.usecase

import com.tgyuu.domain.repository.MemberRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMemberUseCase @Inject constructor(private val memberReposiory: MemberRepository) {
    suspend fun getAllMembers() = flow {
        memberReposiory.getAllMembers().collect {
            emit(it)
        }
    }

    suspend fun getMemberById(id: Int) = flow {
        memberReposiory.getMemberById(id).collect {
            emit(it)
        }
    }
}