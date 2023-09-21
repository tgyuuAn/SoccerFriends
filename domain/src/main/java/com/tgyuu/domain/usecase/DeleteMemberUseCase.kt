package com.tgyuu.domain.usecase

import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.repository.MemberRepository
import javax.inject.Inject

class DeleteMemberUseCase @Inject constructor(private val memberReposiory: MemberRepository) {
    operator suspend fun invoke(member: Member) {
        return memberReposiory.deleteMember(member)
    }
}