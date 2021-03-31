package com.example.movist.presentation.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.movist.R
import com.example.movist.databinding.ActivityDetailBinding
import com.example.movist.presentation.adapter.GenreAdapter
import com.example.movist.presentation.adapter.ReviewListAdapter
import com.example.movist.services.model.detail.Detail
import com.example.movist.services.model.review.Result
import com.example.movist.util.DateFormatParse
import com.example.movist.util.ResultOfNetwork
import com.example.movist.util.TimeParse
import com.example.movist.util.remove
import com.google.android.flexbox.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    private lateinit var _binding : ActivityDetailBinding
    private lateinit var _reviewAdapter: ReviewListAdapter
    private lateinit var _genreAdapter: GenreAdapter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val _viewModel : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val movieId = intent.getIntExtra("movie_id",0)

        _viewModel.getDetail(movieId)
        _viewModel.getReview(movieId)

        with(_binding){
            ibBack.setOnClickListener { finish() }
            initializeAdapter()

            with(bsLayout){
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            }

            _viewModel.detail.observe(this@DetailActivity,{ data ->
                when(data){
                    is ResultOfNetwork.Success -> {
                        initializeDetail(data.value)
                    }
                    is ResultOfNetwork.Failure -> {
                        Timber.d(data.message ?: "Unknown Error")
                    }
                }
            })

            _viewModel.review.observe(this@DetailActivity,{ data ->
                when(data){
                    is ResultOfNetwork.Success -> {
                        val size =data.value.results.size

                        if(size > 0) tvNoReview.remove()
                        if(size <= 5) tvShowAllReview.remove()

                        _reviewAdapter.populateDate(data.value.results)
                    }
                    is ResultOfNetwork.Failure -> {
                        Timber.d(data.message ?: "Unknown Error")
                    }
                }
            })
        }
    }

    private fun initializeAdapter() {
        val layoutManager = FlexboxLayoutManager(this@DetailActivity)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.alignItems = AlignItems.FLEX_START

        with(_binding) {
            _genreAdapter = GenreAdapter()
            _reviewAdapter = ReviewListAdapter(object : ReviewListAdapter.OnReviewClickListener {
                override fun onReviewClick(item: Result) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                    with(bsLayout){
                        tvUsername.text = item.authorDetails.username

                        if (item.authorDetails.rating != null) {
                            tvRating.text = item.authorDetails.rating.toString()
                        }

                        tvDate.text = DateFormatParse.dateWithDayReview(item.createdAt)
                        tvReview.text = item.content
                    }
                }
            })

            rvReviews.adapter = _reviewAdapter
            rvGenres.adapter = _genreAdapter
            rvGenres.layoutManager = layoutManager
        }
    }

    private fun initializeDetail(data : Detail){
        with(_binding){
            tvTitle.text = data.title
            tvCaption.text = data.tagLine
            tvValueOverview.text = data.overview
            tvValueDate.text = DateFormatParse.dateWithDay(data.releaseDate)
            tvValueRunetime.text = TimeParse.minToHours(data.runtime)

            Glide.with(this@DetailActivity)
                .load(BASE_IMAGE_URL + data.posterPath)
                .transform(RoundedCorners(10))
                .placeholder(R.drawable.bg_button_grey_rounded_5dp)
                .into(ivPoster)

            Glide.with(this@DetailActivity)
                .load(BASE_IMAGE_URL + data.backdropPath)
                .placeholder(R.drawable.bg_button_grey_rounded_5dp)
                .into(ivBanner)

            _genreAdapter.populateData(data.genres)
        }
    }
}