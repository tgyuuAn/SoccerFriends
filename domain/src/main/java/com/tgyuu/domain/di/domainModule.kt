package com.tgyuu.domain.di

import com.tgyuu.domain.repository.MemberRepository
import com.tgyuu.domain.usecase.AddMemberUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object domainModule {

    @Provides
    @Singleton
    fun providesAddMemberUseCase(memberRepository: MemberRepository): AddMemberUseCase =
        AddMemberUseCase(memberRepository)
}