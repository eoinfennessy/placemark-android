package org.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.placemark.databinding.ActivityPlacemarkBinding
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i

class PlacemarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacemarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Placemark Activity started..")

        val placemarks = mutableListOf<PlacemarkModel>()

        binding.btnAdd.setOnClickListener {
            val validationErrors = mutableListOf<String>()

            val placemarkTitle = binding.placemarkTitle.text
            if (placemarkTitle.isEmpty()) {
                validationErrors.add("Title is required")
            }

            val placemarkDescription = binding.placemarkDescription.text
            if (placemarkDescription.isEmpty()) {
                validationErrors.add("Description is required")
            }

            if (validationErrors.isNotEmpty()) {
                Snackbar
                    .make(it, validationErrors.joinToString(separator = ";\n"), Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val placemark = PlacemarkModel(placemarkTitle.toString(), placemarkDescription.toString())
            placemarks.add(placemark)
            i("Add Button Pressed: placemarks = $placemarks")
        }
    }
}