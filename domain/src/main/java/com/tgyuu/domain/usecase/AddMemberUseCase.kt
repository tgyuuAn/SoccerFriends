package com.tgyuu.domain.usecase

import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.repository.MemberRepository
import javax.inject.Inject

class AddMemberUseCase @Inject constructor(private val memberReposiory: MemberRepository) {
    operator suspend fun invoke(
        newMemberImage : String,
        newMemberName: String,
        newMemberBackNumber: Int,
        newMemberPosition: String,
        isBenchWarmer: Boolean
    ) {
        val newMember = Member(
            name = newMemberName,
            number = newMemberBackNumber,
            position = newMemberPosition,
            isBenchWarmer = isBenchWarmer,
            image = newMemberImage
        )
        memberReposiory.addNewMember(newMember)
    }
}