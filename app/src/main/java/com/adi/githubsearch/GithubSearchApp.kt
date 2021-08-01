package com.adi.githubsearch

import android.app.Application
import com.adi.githubsearch.api.FlipperWrapper
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GithubSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            FlipperWrapper.setup(this)
        }
    }
}