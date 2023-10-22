package org.wit.placemark.models

class PlacemarkMemStore(private val placemarks: MutableList<PlacemarkModel> = mutableListOf()) : PlacemarkStore {
    override fun find(predicate: (PlacemarkModel) -> Boolean): PlacemarkModel? {
        placemarks.forEach { predicate(it) && return it }
        return null
    }

    override fun findAll(): List<PlacemarkModel> {
        return placemarks.toMutableList()
    }

    override fun create(placemark: PlacemarkModel): PlacemarkModel {
        placemarks.add(placemark)
        return placemark
    }

    override fun update(placemark: PlacemarkModel): PlacemarkModel? {
        val index = placemarks.indexOfFirst { it.id == placemark.id }
        if (index == -1) return null
        placemarks[index] = placemark
        return placemark
    }

    override fun delete(placemark: PlacemarkModel): Boolean {
        return placemarks.remove(placemark)
    }
}