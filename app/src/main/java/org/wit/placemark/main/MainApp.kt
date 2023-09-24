package org.wit.placemark.main

import android.app.Application
import org.wit.placemark.models.PlacemarkMemStore
import timber.log.Timber

class MainApp: Application() {
    val placemark = PlacemarkMemStore()

    override fun onCreate() {
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting Placemark Application")
        super.onCreate()
    }
}