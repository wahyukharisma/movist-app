package com.example.movist.presentation.view.detail

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.movist.R
import com.example.movist.databinding.ActivityDetailBinding
import com.example.movist.presentation.adapter.GenreAdapter
import com.example.movist.presentation.adapter.ReviewListAdapter
import com.example.movist.services.model.detail.Detail
import com.example.movist.services.model.review.Result
import com.example.movist.services.storage.entities.MovieFavorite
import com.example.movist.util.*
import com.example.movist.util.Constants.BASE_IMAGE_URL
import com.google.android.flexbox.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityDetailBinding
    private lateinit var _reviewAdapter: ReviewListAdapter
    private lateinit var _genreAdapter: GenreAdapter
    private lateinit var _movie : MovieFavorite
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val _viewModel : DetailViewModel by viewModels()

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        val movieId = intent.getIntExtra("movie_id",0)

        _viewModel.getDetail(movieId)
        _viewModel.getReview(movieId)
        setFavoriteState(false)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        with(_binding){
            ibBack.setOnClickListener { finish() }
            initializeAdapter()

            ibFavorite.setOnClickListener {
                if(isFavorite){
                    _viewModel.removeFavMovie(_movie)
                    setFavoriteState(false)
                }else{
                    _viewModel.insertFavMovie(_movie)
                    setFavoriteState(true)
                }
            }

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
                        val size = data.value.results.size
                        tvNoReview.show()

                        if(size > 0) tvNoReview.remove()
                        if(size <= 5) tvShowAllReview.remove()

                        _reviewAdapter.removeAll()
                        _reviewAdapter.populateData(data.value.results)
                    }
                    is ResultOfNetwork.Failure -> {
                        Timber.d(data.message ?: "Unknown Error")
                    }
                }
            })

            _viewModel.movie.observe(this@DetailActivity,{ data ->
                if(data != null){
                    setFavoriteState(true)
                }else{
                    setFavoriteState(false)
                }
            })

            _viewModel.movieSaved.observe(this@DetailActivity,{ result ->
                if(result){
                    showMotionSuccess("Movie Saved", "Check your movie in favorite menu")
                }else{
                    showMotionWarning("Sorry, Process Failed","Please try again")
                }
            })

            _viewModel.movieRemove.observe(this@DetailActivity,{ result ->
                if(result){
                    showMotionSuccess("Movie Removed", "This movie no longer available in favorite menu")
                }else{
                    showMotionWarning("Sorry, Process Failed","Please try again")
                }
            })
        }
    }

    private fun setFavoriteState(value : Boolean){
        if(value){
            _binding.ibFavorite.setImageResource(R.drawable.ic_love_active)
        }else{
            _binding.ibFavorite.setImageResource(R.drawable.ic_love_inactive)
        }
        isFavorite = value
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
        _viewModel.getMovieById(data.id)

        with(_binding){
            val date = DateFormatParse.dateWithDay(data.releaseDate)
            tvTitle.text = data.title
            tvCaption.text = data.tagLine
            tvValueOverview.text = data.overview
            tvValueDate.text = date
            tvValueRunetime.text = TimeParse.minToHours(data.runtime)

            val posterUrl = BASE_IMAGE_URL + data.posterPath
            val bannerUrl = BASE_IMAGE_URL + data.backdropPath

            Glide.with(this@DetailActivity)
                .load(posterUrl)
                .transform(RoundedCorners(10))
                .placeholder(R.drawable.bg_button_grey_rounded_5dp)
                .into(ivPoster)

            Glide.with(this@DetailActivity)
                .load(bannerUrl)
                .placeholder(R.drawable.bg_button_grey_rounded_5dp)
                .into(ivBanner)

            _movie = MovieFavorite(
                movieId = data.id,
                title = data.title,
                date = date,
                description = data.overview,
                image = posterUrl)

            _genreAdapter.removeAll()
            _genreAdapter.populateData(data.genres)
        }
    }
}