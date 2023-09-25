package org.wit.placemark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.placemark.R
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

        setSupportActionBar(binding.toolbarAdd)

        binding.btnAdd.setOnClickListener {
            val placemark = PlacemarkModel(
                title = binding.placemarkTitle.text.toString(),
                description = binding.placemarkDescription.text.toString()
            )

            if (placemark.validationErrors.isNotEmpty()) {
                Snackbar
                    .make(it,
                          placemark.validationErrors.joinToString(separator = ";\n"),
                          Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            val newPlacemark = app.placemark.create(placemark)
            i("Add Button Pressed: app.placemarks = ${app.placemark.findAll()}")
            setResult(
                RESULT_OK,
                Intent().putExtra("placemark", newPlacemark)
            )
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cancel_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                setResult(RESULT_CANCELED)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}