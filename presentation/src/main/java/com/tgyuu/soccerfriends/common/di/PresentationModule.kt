package com.tgyuu.soccerfriends.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    @IO
    fun provideIODispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Default
    fun provideDefaultDispatchers(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    @Main
    fun provideMainDispatchers(): CoroutineDispatcher = Dispatchers.Main
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Default

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Main