package com.tgyuu.data.repositoryimpl

import com.tgyuu.data.database.member.MemberEntity
import com.tgyuu.data.database.member.toMember
import com.tgyuu.data.datasource.LocalMemberDataSource
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun getAllMembers() = flow {
        memberDataSource.getAllMembers().collect { memberEntityList ->
            val MemberList = memberEntityList.map { it.toMember() }
            emit(MemberList)
        }
    }

    override fun getMemberById(id: Int): Flow<Member> = flow {
        memberDataSource.getMemberById(id).collect { memberEntity ->
            emit(memberEntity.toMember())
        }
    }
}