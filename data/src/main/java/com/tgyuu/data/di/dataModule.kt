package com.tgyuu.data.di

import android.content.Context
import androidx.room.Room
import com.tgyuu.data.database.member.MemberDao
import com.tgyuu.data.database.SoccerFriendsDatabase
import com.tgyuu.data.database.team.TeamDao
import com.tgyuu.data.datasource.LocalMemberDataSource
import com.tgyuu.data.datasource.LocalTeamDataSource
import com.tgyuu.data.repositoryimpl.MemberRepositoryImpl
import com.tgyuu.data.repositoryimpl.TeamRepositoryImpl
import com.tgyuu.domain.repository.MemberRepository
import com.tgyuu.domain.repository.TeamRepository
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
    fun proivdeSoccerFriendsDatabase(
        @ApplicationContext context: Context
    ): SoccerFriendsDatabase = Room.databaseBuilder(
        context, SoccerFriendsDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideMemberDao(
        database: SoccerFriendsDatabase
    ): MemberDao = database.getMemberDao()

    @Provides
    @Singleton
    fun provideTeamDao(
        database: SoccerFriendsDatabase
    ): TeamDao = database.getTeamDao()

    @Provides
    @Singleton
    fun provideMemberRepository(
        localMemberDataSource: LocalMemberDataSource
    ): MemberRepository = MemberRepositoryImpl(localMemberDataSource)

    @Provides
    @Singleton
    fun provideTeamRepository(
        localTeamDataSource: LocalTeamDataSource
    ): TeamRepository = TeamRepositoryImpl(localTeamDataSource)
}

const val DATABASE_NAME = "soccer_friends_db"