package org.wit.placemark.models

interface PlacemarkStore {
    fun find(predicate: (PlacemarkModel) -> Boolean): PlacemarkModel?
    fun findAll(): List<PlacemarkModel>
    fun create(placemark: PlacemarkModel): PlacemarkModel
    fun update(placemark: PlacemarkModel): PlacemarkModel?
}