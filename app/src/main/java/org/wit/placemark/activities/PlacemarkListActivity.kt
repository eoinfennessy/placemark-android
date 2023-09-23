package org.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.placemark.adapters.PlacemarkAdapter
import org.wit.placemark.databinding.ActivityPlacemarkListBinding
import org.wit.placemark.main.MainApp
import timber.log.Timber

class PlacemarkListActivity : AppCompatActivity() {
    private lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("Starting PlacemarkList Activity")
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = PlacemarkAdapter(app.placemarks)
    }
}