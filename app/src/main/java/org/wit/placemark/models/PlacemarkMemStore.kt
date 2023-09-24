package org.wit.placemark.models

class PlacemarkMemStore(private val placemarks: MutableList<PlacemarkModel> = mutableListOf()) : PlacemarkStore {
    override fun getSize() = placemarks.size

    override fun findAll(): List<PlacemarkModel> {
        return placemarks
    }

    override fun create(placemark: PlacemarkModel) {
        placemarks.add(placemark)
    }
}