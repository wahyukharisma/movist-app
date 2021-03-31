package com.example.movist.presentation.view.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.movist.R
import com.example.movist.databinding.ActivityDashboardBinding
import com.example.movist.databinding.DashboardShimmerBinding
import com.example.movist.presentation.adapter.MovieListAdapter
import com.example.movist.presentation.view.detail.DetailActivity
import com.example.movist.util.ResultOfNetwork
import com.example.movist.util.remove
import com.example.movist.util.show
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityDashboardBinding
    private lateinit var _shimmerBinding : DashboardShimmerBinding
    private lateinit var _adapter: MovieListAdapter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val _viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = _binding.root
        _shimmerBinding = DashboardShimmerBinding.bind(view)
        setContentView(view)

        _viewModel.getPopularMovies()
        isLoadData(true)
        _adapter = MovieListAdapter(object : MovieListAdapter.OnMovieItemClickListener{
            override fun onItemClick(id: Int) {
                startActivity(Intent(this@DashboardActivity, DetailActivity::class.java)
                    .putExtra("movie_id",id))
            }
        })

        with(_binding){
            rvContent.adapter = _adapter

            with(bsLayout){
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

                tvPopular.setOnClickListener { categorySelected(0) }
                tvUpcoming.setOnClickListener { categorySelected(1) }
                tvTopRated.setOnClickListener { categorySelected(2) }
                tvNowPlaying.setOnClickListener { categorySelected(3) }
            }

            btnCategory.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            _viewModel.loadData.observe(this@DashboardActivity,{value ->
                isLoadData(value)
            })

            _viewModel.movies.observe(this@DashboardActivity,{ data ->
                when(data){
                    is ResultOfNetwork.Success -> {
                        _adapter.setItemType(true)
                        _adapter.removeAll()
                        _adapter.populateData(data.value.results)
                    }
                    is ResultOfNetwork.Failure -> {
                        Timber.d(data.message ?: "Unknown Error")
                    }
                }
            })
        }
    }

    /**
     * Set state of loading when get data from API
     */
    private fun isLoadData(value: Boolean){
        if(value){
            _binding.btnCategory.setBackgroundResource(R.drawable.bg_button_blue_rounded_5dp)
            _shimmerBinding.sfContent.startShimmer()
            _shimmerBinding.sfContent.show()
        }else{
            _binding.btnCategory.setBackgroundResource(R.drawable.bg_button_blue_rounded_5dp)
            _shimmerBinding.sfContent.stopShimmer()
            _shimmerBinding.sfContent.remove()
        }
        _binding.btnCategory.isEnabled = !value
    }

    /**
     * Category action selected, call API based on index category selected
     *
     * 0 -> popular
     * 1 -> upcoming
     * 2 -> top rated
     * 3 -> now playing
     */
    private fun categorySelected(index : Int){
        when(index){
            0 -> {
                _viewModel.getPopularMovies()
            }
            1 -> {
                _viewModel.getNowPlayingMovies()
            }
            2 -> {
                _viewModel.getTopRatedMovies()
            }
            3 -> {
                _viewModel.getUpcomingMovies()
            }
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        changeCategoryActiveColor(index)
    }

    /**
     * Change text active color based on index selected
     *
     * 0 -> popular
     * 1 -> upcoming
     * 2 -> top rated
     * 3 -> now playing
     */
    private fun changeCategoryActiveColor(index: Int){
        with(_binding){
            with(bsLayout){
                tvPopular.setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.black_text))
                tvUpcoming.setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.black_text))
                tvTopRated.setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.black_text))
                tvNowPlaying.setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.black_text))

                when(index){
                    0 -> {
                        tvPopular.setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.curious_blue))
                    }
                    1 -> {
                        tvUpcoming.setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.curious_blue))
                    }
                    2 -> {
                        tvTopRated.setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.curious_blue))
                    }
                    3 -> {
                        tvNowPlaying.setTextColor(ContextCompat.getColor(this@DashboardActivity, R.color.curious_blue))
                    }
                }
            }
        }
    }
}