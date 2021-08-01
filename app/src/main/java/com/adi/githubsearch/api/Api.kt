package com.adi.githubsearch.api

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class Api {
    companion object {
        const val DEFAULT_BASE_URL = "https://api.github.com/"
        const val TIMEOUT = 60000

        @Synchronized
        fun getDefaultClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(FlipperWrapper.flipperNetworkInterceptor)
                .build()
        }
    }
}