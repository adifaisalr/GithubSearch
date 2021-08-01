package com.adi.githubsearch.di

import com.adi.githubsearch.api.GithubService
import com.adi.githubsearch.data.repository.UserRepositoryImpl
import com.adi.githubsearch.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideEmailRepository(githubService: GithubService): UserRepository {
        return UserRepositoryImpl(githubService)
    }
}