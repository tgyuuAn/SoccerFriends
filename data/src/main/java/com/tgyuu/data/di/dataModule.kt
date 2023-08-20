package com.tgyuu.data.di

import android.content.Context
import androidx.room.Room
import com.tgyuu.data.database.MemberDao
import com.tgyuu.data.database.MemberDatabase
import com.tgyuu.data.datasource.LocalMemberDataSource
import com.tgyuu.data.repositoryimpl.MemberRepositoryImpl
import com.tgyuu.domain.team.repository.MemberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object dataModule {

    @Provides
    @Singleton
    fun proivdeMemberDatabase(
        @ApplicationContext context: Context
    ): MemberDatabase =
        Room.databaseBuilder(context, MemberDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideMemberDao(
        database: MemberDatabase
    ) = database.getMemberDao()

    @Provides
    @Singleton
    fun provideLocalMemberDataSource(
        memberDao: MemberDao
    ) : LocalMemberDataSource = LocalMemberDataSource(memberDao)

    @Provides
    @Singleton
    fun provideMemberRepository(
        localRemoteDataSource: LocalMemberDataSource
    ): MemberRepository = MemberRepositoryImpl(localRemoteDataSource)
}

const val DATABASE_NAME = "member_db"