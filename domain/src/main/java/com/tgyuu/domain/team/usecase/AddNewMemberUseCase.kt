package com.tgyuu.domain.team.usecase

import com.tgyuu.domain.team.entity.Member
import com.tgyuu.domain.team.repository.MemberReposiory
import javax.inject.Inject

class AddNewMemberUseCase @Inject constructor(private val memberReposiory: MemberReposiory) {
    operator suspend fun invoke(
        newMemberName: String,
        newMemberBackNumber: Int,
        newMemberPosition: String,
        isBenchWarmer: Boolean
    ): Result<Unit> {
        val newMember = Member(
            name = newMemberName,
            number = newMemberBackNumber,
            position = newMemberPosition,
            isBenchWarmer = isBenchWarmer,
            image = ""
        )
        return memberReposiory.addNewMember(newMember)
            .fold(onSuccess = { Result.success(it) }, onFailure = { Result.failure(it) })
    }
}