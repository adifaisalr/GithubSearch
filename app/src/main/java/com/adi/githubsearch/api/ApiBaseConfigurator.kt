package com.adi.githubsearch.api

import okhttp3.OkHttpClient

interface ApiBaseConfigurator {
    fun newHttpClientBuilder(): OkHttpClient.Builder
}