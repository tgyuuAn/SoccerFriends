package com.tgyuu.domain.usecase

import com.tgyuu.domain.repository.MemberRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMemberUseCase @Inject constructor(private val memberReposiory: MemberRepository) {
    operator suspend fun invoke() = flow {
        memberReposiory.getAllMembers().collect {
            emit(it)
        }
    }
}