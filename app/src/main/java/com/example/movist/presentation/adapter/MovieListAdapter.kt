package com.example.movist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.movist.R
import com.example.movist.databinding.ItemMovieBinding
import com.example.movist.services.model.movie.Result
import com.example.movist.util.DateFormatParse
import com.example.movist.util.remove
import com.example.movist.util.show


class MovieListAdapter(var onItemClickListener: OnMovieItemClickListener)
    : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    companion object {
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    private val items : MutableList<Result> = ArrayList()

    /**
     * isDefault represent which card setting will be show
     * if true : Default card movie list in dashboard
     * if false : Card Movie list in favorite
     */
    private var isDefault : Boolean = true

    fun setItemType(type: Boolean){
        isDefault = type
        notifyDataSetChanged()
    }

    fun populateData(data: List<Result>){
        this.items.addAll(data)
        notifyDataSetChanged()
    }

    fun removeAll(){
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            val item = items[position]
            with(binding){
                if(isDefault){
                    ibFavorite.remove()
                }else{
                    ibFavorite.show()
                }

                tvTitle.text = item.title
                tvDescription.text = item.overview
                tvDate.text = DateFormatParse.dateWithDay(item.releaseDate)

                Glide.with(itemView.context)
                    .load(BASE_IMAGE_URL + item.posterPath)
                    .transform(RoundedCorners(10))
                    .placeholder(R.drawable.bg_button_grey_rounded_5dp)
                    .into(ivPoster)

                cvItem.setOnClickListener {
                    onItemClickListener.onItemClick(item.id)
                }
            }
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
      val binding = ItemMovieBinding.bind(view)
    }

    interface OnMovieItemClickListener {
        fun onItemClick(id : Int)
    }
}