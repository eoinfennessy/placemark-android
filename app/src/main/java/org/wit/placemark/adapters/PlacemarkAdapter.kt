package org.wit.placemark.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.placemark.databinding.CardPlacemarkBinding
import org.wit.placemark.models.PlacemarkModel

class PlacemarkAdapter(private val placemarks: List<PlacemarkModel>) : RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {
    override fun getItemCount(): Int = placemarks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlacemarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        return holder.bind(placemark)
    }

    class MainHolder(private val binding: CardPlacemarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placemark: PlacemarkModel) {
            binding.placemarkTitle.text = placemark.title
            binding.description.text = placemark.description
        }
    }
}