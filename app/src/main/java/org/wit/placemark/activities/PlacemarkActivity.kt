package org.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.placemark.databinding.ActivityPlacemarkBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber.i

class PlacemarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacemarkBinding
    private lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        i("Starting Placemark Activity")
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        binding.btnAdd.setOnClickListener {
            val placemark = PlacemarkModel(
                title = binding.placemarkTitle.text.toString(),
                description = binding.placemarkDescription.text.toString()
            )

            if (placemark.validationErrors.isNotEmpty()) {
                Snackbar
                    .make(
                        it,
                        placemark.validationErrors.joinToString(separator = ";\n"),
                        Snackbar.LENGTH_LONG
                    )
                    .show()
                return@setOnClickListener
            }

            app.placemarks.add(placemark)
            i("Add Button Pressed: app.placemarks = ${app.placemarks}")
        }
    }
}