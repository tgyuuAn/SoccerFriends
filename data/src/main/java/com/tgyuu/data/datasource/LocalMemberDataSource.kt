package com.tgyuu.data.datasource


import com.tgyuu.data.database.MemberDao
import com.tgyuu.data.database.MemberEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMemberDataSource @Inject constructor(private val memberDao: MemberDao) {

    suspend fun insertMember(member : MemberEntity) {
        return memberDao.insertMember(member)
    }

    suspend fun deleteMember(member : MemberEntity) {
        return memberDao.deleteMember(member)
    }

    fun getAllMembers(): Flow<List<MemberEntity>> {
        return memberDao.getAllMembers()
    }
}