package org.wit.placemark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.placemark.R
import org.wit.placemark.databinding.ActivityMapsBinding
import org.wit.placemark.models.Location

class MapActivity :
    AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var location: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        location = intent.extras?.getParcelable("location", Location::class.java) ?: Location()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val loc = LatLng(location.lat, location.lng)
        val markerOptions = MarkerOptions()
            .position(loc)
            .title("Placemark")
            .snippet("GPS: $loc")
            .draggable(true)
        mMap.addMarker(markerOptions)
        mMap.setOnMarkerDragListener(this)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    override fun onMarkerDrag(marker: Marker) {}

    override fun onMarkerDragStart(marker: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = mMap.cameraPosition.zoom
        val loc = LatLng(location.lat, location.lng)
        marker.snippet = "GPS: $loc"
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }
}