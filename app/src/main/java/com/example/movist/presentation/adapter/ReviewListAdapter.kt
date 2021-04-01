package com.example.movist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movist.R
import com.example.movist.databinding.ItemReviewBinding
import com.example.movist.services.model.review.Result
import com.example.movist.util.DateFormatParse

class ReviewListAdapter(private val onReviewClickListener : OnReviewClickListener)
    : RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {

    private val items : MutableList<Result> = ArrayList()
    private val limit = 5

    fun populateData(data : List<Result>){
        this.items.addAll(data)
        notifyDataSetChanged()
    }

    fun removeAll(){
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_review, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            val item = items[position]
            with(binding){
                tvUsername.text = item.authorDetails.username

                if(item.authorDetails.rating != null){
                    tvRating.text = item.authorDetails.rating.toString()
                }

                tvDate.text = DateFormatParse.dateWithDayReview(item.createdAt)
                tvReview.text = item.content

                clItem.setOnClickListener { onReviewClickListener.onReviewClick(item) }
            }
        }
    }

    override fun getItemCount() = items.size.coerceAtMost(limit)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemReviewBinding.bind(view)
    }

    interface OnReviewClickListener{
        fun onReviewClick(item: Result)
    }
}