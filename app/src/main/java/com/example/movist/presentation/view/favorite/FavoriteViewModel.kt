package com.example.movist.presentation.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movist.repository.MovieFavoriteRepository
import com.example.movist.services.storage.entities.MovieFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
@Inject
constructor(
    private val repository : MovieFavoriteRepository
) : ViewModel() {

    val movies: LiveData<List<MovieFavorite>> get() = mutableMovies
    private val mutableMovies = MutableLiveData<List<MovieFavorite>>()

    val movieRemove: LiveData<Boolean> get() = mutableMovieRemoved
    private val mutableMovieRemoved = MutableLiveData<Boolean>()

    val movieClear: LiveData<Boolean> get() = mutableMovieClear
    private val mutableMovieClear = MutableLiveData<Boolean>()

    /**
     * Get list of favorite movies from local storage
     *
     * Output : List of [MovieFavorite]
     */
    fun getFavMovie(){
        viewModelScope.launch {
            try{
                mutableMovies.postValue(repository.getMovies())
            }catch (throwable: Throwable){
                Timber.d("Result -> failed")
            }
        }
    }

    /**
     * Remove favorite movie from local database
     *
     * @param data movie id
     */
    fun removeFavMovie(data : MovieFavorite){
        viewModelScope.launch {
            try{
                repository.removeMovie(data)
                mutableMovieRemoved.postValue(true)
            }catch (throwable : Throwable){
                mutableMovieRemoved.postValue(false)
            }
        }
    }

    /**
     * Remove all favorite movie from local database
     *
     */
    fun removeAllFavMovie(){
        viewModelScope.launch {
            try{
                repository.removeAllMovie()
                mutableMovieClear.postValue(true)
            }catch (throwable : Throwable){
                mutableMovieClear.postValue(false)
            }
        }
    }
}