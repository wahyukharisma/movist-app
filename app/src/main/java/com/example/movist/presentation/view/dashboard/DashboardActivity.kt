package com.example.movist.presentation.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.movist.R
import com.example.movist.databinding.ActivityDashboardBinding
import com.example.movist.databinding.DashboardShimmerBinding
import com.example.movist.presentation.adapter.MovieListAdapter
import com.example.movist.util.ResultOfNetwork
import com.example.movist.util.remove
import com.example.movist.util.show
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityDashboardBinding
    private lateinit var _shimmerBinding : DashboardShimmerBinding
    private lateinit var _adapter: MovieListAdapter

    private val _viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = _binding.root
        _shimmerBinding = DashboardShimmerBinding.bind(view)
        setContentView(view)

        _viewModel.getPopularMovies()
        isLoadData(true)
        _adapter = MovieListAdapter()

        with(_binding){
            rvContent.adapter = _adapter

            _viewModel.movies.observe(this@DashboardActivity,{ data ->
                when(data){
                    is ResultOfNetwork.Success -> {
                       isLoadData(false)
                        _adapter.setItemType(true)
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
            _binding.btnApply.setBackgroundResource(R.drawable.bg_button_blue_rounded_5dp)
            _shimmerBinding.sfContent.startShimmer()
            _shimmerBinding.sfContent.show()
        }else{
            _binding.btnApply.setBackgroundResource(R.drawable.bg_button_blue_rounded_5dp)
            _shimmerBinding.sfContent.stopShimmer()
            _shimmerBinding.sfContent.remove()
        }
        _binding.btnApply.isEnabled = value
    }
}