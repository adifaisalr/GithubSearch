package com.adi.githubsearch.di

import com.adi.githubsearch.api.Api
import com.adi.githubsearch.api.GithubService
import com.adi.githubsearch.api.MyCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideMidasService(): GithubService {
        return Retrofit.Builder()
            .baseUrl(Api.DEFAULT_BASE_URL)
            .client(Api.getDefaultClient())
            .addCallAdapterFactory(MyCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubService::class.java)
    }
}
