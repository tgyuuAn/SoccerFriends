package com.tgyuu.soccerfriends.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    @Named("IODispatchers")
    fun provideIODispatchers() : CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Named("DefaultDispatchers")
    fun provideDefaultDispatchers() : CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    @Named("MainDispatchers")
    fun provideMainDispatchers() : CoroutineDispatcher = Dispatchers.Main
}