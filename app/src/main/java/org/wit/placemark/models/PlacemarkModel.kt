package org.wit.placemark.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlacemarkModel(val id: String,
                          val title: String,
                          val description: String,
                          val location: Location,
                          val image: Uri = Uri.EMPTY) : Parcelable {
    fun getValidationErrors(): MutableList<String> {
        val validationErrors = mutableListOf<String>()
        if (title.isEmpty()) validationErrors.add("Title is required")
        if (description.isEmpty()) validationErrors.add("Description is required")
        if (location.isDefault()) validationErrors.add("Please set a location")
        return validationErrors
    }
}
