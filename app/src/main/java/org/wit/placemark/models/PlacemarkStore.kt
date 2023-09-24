package org.wit.placemark.models

interface PlacemarkStore {
    fun getSize(): Int
    fun findAll(): List<PlacemarkModel>
    fun create(placemark: PlacemarkModel)
}