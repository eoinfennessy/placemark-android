package org.wit.placemark.main

import android.app.Application
import org.wit.placemark.models.PlacemarkJsonStore
import org.wit.placemark.models.PlacemarkStore
import timber.log.Timber

class MainApp: Application() {
    lateinit var placemarkStore: PlacemarkStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting Placemark Application")
        placemarkStore = PlacemarkJsonStore(applicationContext)
    }
}