package com.example.movist.presentation.view.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.movist.databinding.ActivityFavoriteMoviesBinding
import com.example.movist.presentation.adapter.MovieFavAdapter
import com.example.movist.services.storage.entities.MovieFavorite
import com.example.movist.util.remove
import com.example.movist.util.show
import com.example.movist.util.showMotionSuccess
import com.example.movist.util.showMotionWarning
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityFavoriteMoviesBinding
    private lateinit var _adapter : MovieFavAdapter

    private val _viewModel : FavoriteViewModel by viewModels()

    private var size = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteMoviesBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)

        _viewModel.getFavMovie()

        with(_binding){
            ibBack.setOnClickListener { finish() }
            btnShowMovie.setOnClickListener { finish() }
            ibTrash.isEnabled = false

            _adapter = MovieFavAdapter(object: MovieFavAdapter.OnMovieClickListener{
                override fun removeMovie(movie: MovieFavorite, mSize: Int) {
                    _viewModel.removeFavMovie(movie)
                    size = mSize
                }
            })

            ibTrash.setOnClickListener {
                _viewModel.removeAllFavMovie()
            }

            rvContent.adapter = _adapter

            _viewModel.movies.observe(this@FavoriteActivity,{ data ->
                if(data.isNotEmpty()){
                    clEmptyFavorite.remove()
                    ibTrash.isEnabled = true
                    _adapter.populatedData(data)
                }
            })

            _viewModel.movieRemove.observe(this@FavoriteActivity,{ result ->
                if(result){
                    if(size == 1){
                        clEmptyFavorite.show()
                    }
                    showMotionSuccess("Movie Removed", "This movie no longer available in favorite menu")
                }else{
                    showMotionWarning("Sorry, Process Failed","Please try again")
                }
            })

            _viewModel.movieClear.observe(this@FavoriteActivity,{ result ->
                if(result){
                    _adapter.removeAll()
                    clEmptyFavorite.show()
                    showMotionSuccess("Movie Removed", "All movie no longer available in favorite menu")
                }else{
                    showMotionWarning("Sorry, Process Failed","Please try again")
                }
            })
        }
    }
}