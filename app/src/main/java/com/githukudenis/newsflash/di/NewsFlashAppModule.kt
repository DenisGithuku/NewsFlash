package com.githukudenis.newsflash.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsFlashAppModule {

    @Singleton
    @Provides
    fun provideNewsApi() {

    }

}