package com.tgyuu.domain.usecase

import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.repository.MemberRepository
import javax.inject.Inject

class UpdateMemberInformationUseCase @Inject constructor(private val memberReposiory: MemberRepository) {
    suspend fun updateMemberName(member: Member, name: String) {
        memberReposiory.updateMember(
            Member(
                name = name,
                image = member.image,
                position = member.position,
                number = member.number,
                isBenchWarmer = member.isBenchWarmer
            )
        )
    }
}