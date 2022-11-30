package com.test.conversion

import android.app.Application


class ConversionApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: ConversionApp
    }
}
