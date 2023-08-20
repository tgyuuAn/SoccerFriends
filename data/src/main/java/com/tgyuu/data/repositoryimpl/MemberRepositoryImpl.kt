package com.tgyuu.data.repositoryimpl

import com.tgyuu.data.database.MemberEntity
import com.tgyuu.data.database.toMember
import com.tgyuu.data.datasource.LocalMemberDataSource
import com.tgyuu.domain.entity.Member
import com.tgyuu.domain.repository.MemberRepository
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

    override fun getAllMembers() = flow {
        memberDataSource.getAllMembers().collect { MemberEntityList ->
            val MemberList = MemberEntityList.map { it.toMember() }
            emit(MemberList)
        }
    }
}