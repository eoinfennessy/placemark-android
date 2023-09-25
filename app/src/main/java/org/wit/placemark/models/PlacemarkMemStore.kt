package org.wit.placemark.models

class PlacemarkMemStore(private val placemarks: MutableList<PlacemarkModel> = mutableListOf()) : PlacemarkStore {
    override fun findAll(): List<PlacemarkModel> {
        return placemarks.toMutableList()
    }

    override fun create(placemark: PlacemarkModel): PlacemarkModel {
        placemarks.add(placemark)
        return placemark
    }
}