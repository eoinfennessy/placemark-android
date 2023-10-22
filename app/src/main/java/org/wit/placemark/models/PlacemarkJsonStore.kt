package org.wit.placemark.models

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import org.wit.placemark.helpers.exists
import org.wit.placemark.helpers.read
import org.wit.placemark.helpers.write
import java.lang.reflect.Type

class PlacemarkJsonStore(private val context: Context) : PlacemarkStore {
    private val jsonFilename = "placemarks.json"
    private val listType: Type = object : TypeToken<ArrayList<PlacemarkModel>>() {}.type
    private val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
        .registerTypeAdapter(Uri::class.java, UriParser())
        .create()
    private var placemarks = mutableListOf<PlacemarkModel>()

    init {
        if (exists(context, jsonFilename)) {
            deserialize()
        }
    }

    override fun find(predicate: (PlacemarkModel) -> Boolean): PlacemarkModel? {
        placemarks.forEach { predicate(it) && return it }
        return null
    }

    override fun findAll(): MutableList<PlacemarkModel> {
        return placemarks
    }

    override fun create(placemark: PlacemarkModel): PlacemarkModel {
        placemarks.add(placemark)
        serialize()
        return placemark
    }


    override fun update(placemark: PlacemarkModel): PlacemarkModel? {
        val index = placemarks.indexOfFirst { it.id == placemark.id }
        if (index == -1) return null
        placemarks[index] = placemark
        serialize()
        return placemark
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(placemarks, listType)
        write(context, jsonFilename, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, jsonFilename)
        placemarks = gsonBuilder.fromJson(jsonString, listType)
    }
}

class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}