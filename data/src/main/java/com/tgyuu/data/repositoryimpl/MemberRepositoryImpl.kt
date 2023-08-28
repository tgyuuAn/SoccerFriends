package com.tgyuu.data.repositoryimpl

import com.tgyuu.data.database.member.MemberEntity
import com.tgyuu.data.database.member.toMember
import com.tgyuu.data.datasource.LocalMemberDataSource
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(private val memberDataSource: LocalMemberDataSource) :
    MemberRepository {
    override suspend fun addNewMember(member: Member) {
        val memberEntity = MemberEntity(
            name = member.name,
            image = member.image,
            position = member.position,
            number = member.number,
            isBenchWarmer = member.isBenchWarmer
        )
        memberDataSource.insertMember(memberEntity)
    }

    override suspend fun deleteMember(member: Member) {
        val memberEntity = MemberEntity(
            id = member.id,
            name = member.name,
            image = member.image,
            position = member.position,
            number = member.number,
            isBenchWarmer = member.isBenchWarmer
        )
        memberDataSource.deleteMember(memberEntity)
    }

    override suspend fun updateMember(member: Member) {
        val memberEntity = MemberEntity(
            id = member.id,
            name = member.name,
            image = member.image,
            position = member.position,
            number = member.number,
            isBenchWarmer = member.isBenchWarmer
        )
        memberDataSource.updateMember(memberEntity)
    }

    override fun getAllMembers() : Flow<List<Member>> {
        return memberDataSource.getAllMembers().map { memberEntityList ->
            memberEntityList.map { it.toMember() }
        }
    }

    override fun getMemberById(id: Int) : Flow<Member> {
        return memberDataSource.getMemberById(id).map {
            it.toMember()
        }
    }
}