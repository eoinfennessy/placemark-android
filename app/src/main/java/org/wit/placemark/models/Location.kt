package org.wit.placemark.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable {
    fun isDefault(): Boolean {
        return lat + lng + zoom == 0.0
    }
}
