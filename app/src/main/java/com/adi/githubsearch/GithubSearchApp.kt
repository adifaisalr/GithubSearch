package com.adi.githubsearch

import android.app.Application
import com.adi.githubsearch.api.Api
import com.adi.githubsearch.api.ApiBaseConfigurator
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
open class GithubSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Api.setBaseConfigurator(object : ApiBaseConfigurator {
            override fun newHttpClientBuilder(): OkHttpClient.Builder = httpClientBuilder()
        })
    }

    open fun httpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }
}