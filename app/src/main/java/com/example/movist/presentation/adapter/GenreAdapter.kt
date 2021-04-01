package com.example.movist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movist.R
import com.example.movist.databinding.ItemGenreBinding
import com.example.movist.services.model.detail.Genre

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private val items : MutableList<Genre> = ArrayList()

    fun populateData(data : List<Genre>){
        this.items.addAll(data)
        notifyDataSetChanged()
    }

    fun removeAll(){
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_genre, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            val item = items[position]
            with(binding){
                tvGenre.text = item.name
            }
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemGenreBinding.bind(view)
    }
}