package org.wit.placemark.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.placemark.R
import org.wit.placemark.databinding.ActivityPlacemarkBinding
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber.i
import java.util.UUID

class PlacemarkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacemarkBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var app: MainApp
    private var location = Location()
    private var imageUri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        i("Starting Placemark Activity")
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerImagePickerCallback()
        registerMapCallback()
        app = application as MainApp

        val isEditMode = intent.hasExtra("placemark_edit")
        var placemarkId: String? = null

        if (isEditMode) {
            val placemark = intent.extras?.getParcelable("placemark_edit", PlacemarkModel::class.java)
                ?: throw Exception("Value of Parcelable 'placemark_edit' is not of type PlacemarkModel")
            placemarkId = placemark.id
            binding.toolbarAdd.title = "Update \"${placemark.title}\""
            binding.btnAdd.text = getString(R.string.update_placemark)
            binding.placemarkTitle.setText(placemark.title)
            binding.placemarkDescription.setText(placemark.description)
            location = placemark.location
            if (placemark.image != Uri.EMPTY) {
                imageUri = placemark.image
                binding.chooseImage.text = getString(R.string.update_image)
                Picasso.get()
                       .load(placemark.image)
                       .into(binding.placemarkImage)
            }
        }

        setSupportActionBar(binding.toolbarAdd)

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
        }

        binding.placemarkLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
            launcherIntent.putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        binding.btnAdd.setOnClickListener {
            val placemark = PlacemarkModel(
                id = placemarkId ?: UUID.randomUUID().toString(),
                title = binding.placemarkTitle.text.toString(),
                description = binding.placemarkDescription.text.toString(),
                location = location,
                image = imageUri
            )

            val validationErrors = placemark.getValidationErrors()
            if (validationErrors.isNotEmpty()) {
                Snackbar
                    .make(it,
                          validationErrors.joinToString(separator = ";\n"),
                          Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            val newPlacemark = if (isEditMode) {
                app.placemarkStore.update(placemark)
            } else {
                app.placemarkStore.create(placemark)
            }
            i("Add/Update Button Pressed: app.placemarks = ${app.placemarkStore.findAll()}")
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
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode) {
                    RESULT_OK -> {
                        val uri = result.data?.data
                        if (uri == null) {
                            i("Result contains a null image URI")
                            return@registerForActivityResult
                        }
                        contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        imageUri = uri
                        Picasso.get()
                            .load(uri)
                            .into(binding.placemarkImage)
                        binding.chooseImage.text = getString(R.string.choose_a_different_image)
                    }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode) {
                    RESULT_OK -> {
                        val loc = result.data?.extras?.getParcelable("location", Location::class.java)
                        if (loc == null) {
                            i("Result contains a null location")
                            return@registerForActivityResult
                        }
                        i("loc: $loc")
                        location = loc
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
}