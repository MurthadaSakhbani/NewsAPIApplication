package com.example.newsapiapplication

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.facebook.drawee.backends.pipeline.Fresco

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(this)
        Fresco.initialize(this)
    }
}