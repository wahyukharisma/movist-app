package com.example.movist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.movist.R
import com.example.movist.databinding.ItemMovieBinding
import com.example.movist.services.storage.entities.MovieFavorite

class MovieFavAdapter(private val onMovieClickListener : OnMovieClickListener)
    : RecyclerView.Adapter<MovieFavAdapter.ViewHolder>() {

    private val items : MutableList<MovieFavorite> = ArrayList()

    fun populatedData(data : List<MovieFavorite>){
        this.items.addAll(data)
        notifyDataSetChanged()
    }

    fun removeAll(){
        this.items.clear()
        notifyDataSetChanged()
    }

    private fun removeSingle(data : MovieFavorite){
        this.items.remove(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_movie, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            val item = items[position]
            with(binding){
                tvTitle.text = item.title
                tvDate.text = item.date
                tvDescription.text = item.description

                Glide.with(itemView.context)
                    .load( item.image)
                    .transform(RoundedCorners(10))
                    .placeholder(R.drawable.bg_button_grey_rounded_5dp)
                    .into(ivPoster)

                ibFavorite.setOnClickListener {
                    onMovieClickListener.removeMovie(item, items.size)
                    removeSingle(item)
                }
            }
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemMovieBinding.bind(view)
    }

    interface OnMovieClickListener{
        fun removeMovie(movie: MovieFavorite, size : Int)
    }
}