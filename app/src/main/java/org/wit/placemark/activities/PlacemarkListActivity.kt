package org.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.placemark.R
import org.wit.placemark.adapters.PlacemarkAdapter
import org.wit.placemark.adapters.PlacemarkListener
import org.wit.placemark.databinding.ActivityPlacemarkListBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber.i

class PlacemarkListActivity : AppCompatActivity(), PlacemarkListener {
    private lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding
    private lateinit var placemarks: MutableList<PlacemarkModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        i("Starting PlacemarkList Activity")
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        placemarks = app.placemark.findAll().toMutableList()

        setSupportActionBar(binding.toolbarAdd)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = PlacemarkAdapter(placemarks, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, PlacemarkActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val placemark = it.data?.getParcelableExtra("placemark", PlacemarkModel::class.java)
                ?: return@registerForActivityResult
            placemarks.add(placemark)
            binding.recyclerView.adapter?.notifyItemRangeChanged(0, placemarks.size)
        }
    }

    override fun onPlacemarkClick(placemark: PlacemarkModel) {
        val launcherIntent = Intent(this, PlacemarkActivity::class.java)
        launcherIntent.putExtra("placemark_edit", placemark)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val placemark = result.data?.getParcelableExtra("placemark", PlacemarkModel::class.java)
                ?: throw Exception("Value of Parcelable 'placemark' is not of type PlacemarkModel")
            val index = placemarks.indexOfFirst { it.id == placemark.id }
            if (index == -1) throw Exception("No placemark with ID ${placemark.id} exists in 'placemarks'")
            placemarks[index] = placemark
            binding.recyclerView.adapter?.notifyItemChanged(index)
        }
    }
}