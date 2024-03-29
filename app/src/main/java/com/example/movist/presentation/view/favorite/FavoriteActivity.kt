package com.example.movist.presentation.view.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movist.databinding.ActivityFavoriteMoviesBinding
import com.example.movist.presentation.adapter.MovieFavAdapter
import com.example.movist.presentation.view.detail.DetailActivity
import com.example.movist.services.storage.entities.MovieFavorite
import com.example.movist.util.remove
import com.example.movist.util.show
import com.example.movist.util.showMotionSuccess
import com.example.movist.util.showMotionWarning
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityFavoriteMoviesBinding
    private lateinit var _adapter : MovieFavAdapter

    private val _viewModel : FavoriteViewModel by viewModels()

    private var size = 0

    override fun onResume() {
        super.onResume()
        _viewModel.getFavMovie()
    }

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

                override fun onItemClick(id: Int) {
                    startActivity(Intent(this@FavoriteActivity, DetailActivity::class.java)
                            .putExtra("movie_id",id))
                }
            })

            ibTrash.setOnClickListener {
                _viewModel.removeAllFavMovie()
            }

            rvContent.adapter = _adapter

            _viewModel.movies.observe(this@FavoriteActivity,{ data ->
                _adapter.removeAll()

                if(data.isNotEmpty()){
                    clEmptyFavorite.remove()
                    ibTrash.isEnabled = true
                    _adapter.populatedData(data)
                }else{
                    clEmptyFavorite.show()
                    ibTrash.isEnabled = false
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