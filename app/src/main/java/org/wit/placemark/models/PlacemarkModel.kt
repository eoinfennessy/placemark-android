package org.wit.placemark.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlacemarkModel(val title: String, val description: String) : Parcelable {
    @IgnoredOnParcel
    val validationErrors = mutableListOf<String>()
    init {
        if (title.isEmpty()) {
            validationErrors.add("Title is required")
        }
        if (description.isEmpty()) {
            validationErrors.add("Description is required")
        }
    }
}
