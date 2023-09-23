package org.wit.placemark.models

data class PlacemarkModel(val title: String, val description: String) {
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
