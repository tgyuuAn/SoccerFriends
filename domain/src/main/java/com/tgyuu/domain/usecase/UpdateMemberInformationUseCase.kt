package com.tgyuu.domain.usecase

import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.repository.MemberRepository
import javax.inject.Inject

class UpdateMemberInformationUseCase @Inject constructor(private val memberReposiory: MemberRepository) {
    suspend fun updateMemberName(member: Member, name: String) {
        return memberReposiory.updateMember(
            Member(
                id = member.id,
                name = name,
                image = member.image,
                position = member.position,
                number = member.number,
                isBenchWarmer = member.isBenchWarmer
            )
        )
    }

    suspend fun updateMemberImage(member: Member, image: String) {
        return memberReposiory.updateMember(
            Member(
                id = member.id,
                name = member.name,
                image = image,
                position = member.position,
                number = member.number,
                isBenchWarmer = member.isBenchWarmer
            )
        )
    }

    suspend fun removeMemberImage(member: Member) {
        return memberReposiory.updateMember(
            Member(
                id = member.id,
                name = member.name,
                image = "",
                position = member.position,
                number = member.number,
                isBenchWarmer = member.isBenchWarmer
            )
        )
    }

    suspend fun updateMemberPosition(member: Member, position: String) {
        return memberReposiory.updateMember(
            Member(
                id = member.id,
                name = member.name,
                image = member.image,
                position = position,
                number = member.number,
                isBenchWarmer = member.isBenchWarmer
            )
        )
    }

    suspend fun updateMemberNumber(member: Member, number : Int) {
        return memberReposiory.updateMember(
            Member(
                id = member.id,
                name = member.name,
                image = member.image,
                position = member.position,
                number = number,
                isBenchWarmer = member.isBenchWarmer
            )
        )
    }

    suspend fun updateMemberIsBenchWarmer(member: Member, isBenchWarmer : Boolean) {
        return memberReposiory.updateMember(
            Member(
                id = member.id,
                name = member.name,
                image = member.image,
                position = member.position,
                number = member.number,
                isBenchWarmer = isBenchWarmer
            )
        )
    }
}