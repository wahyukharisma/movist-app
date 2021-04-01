package com.example.movist.presentation.view.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movist.repository.MovieRepository
import com.example.movist.services.model.movie.Movie
import com.example.movist.util.ResultOfNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DashboardViewModel
@Inject
constructor(
    private val repository: MovieRepository,
    @Named("token") private val token : String
) : ViewModel() {
    val movies : LiveData<ResultOfNetwork<Movie>>
    val loadData : LiveData<Boolean>

    init {
        this.movies = repository.movies
        this.loadData = repository.loadData
    }

    /**
     * Get list of popular movies from API
     *
     * Output : Result of response [Movie] object handle using sealed class
     *
     */
    fun getPopularMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repository.getPopularMovies(token)
                repository.loadData.postValue(false)
            }catch (throwable: Throwable){
                repository.loadData.postValue(false)
                throwableCase(throwable)
            }
        }
    }

    /**
     * Get list of now playing movies from API
     *
     * Output : Result of response [Movie] object handle using sealed class
     *
     */
    fun getNowPlayingMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repository.getNowPlayingMovies(token)
                repository.loadData.postValue(false)
            }catch (throwable: Throwable){
                repository.loadData.postValue(false)
                throwableCase(throwable)
            }
        }
    }

    /**
     * Get list of upcoming movies from API
     *
     * Output : Result of response [Movie] object handle using sealed class
     *
     */
    fun getUpcomingMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repository.getUpcomingMovies(token)
                repository.loadData.postValue(false)
            }catch (throwable: Throwable){
                repository.loadData.postValue(false)
                throwableCase(throwable)
            }
        }
    }

    /**
     * Get list of top rated movies from API
     *
     * Output : Result of response [Movie] object handle using sealed class
     *
     */
    fun getTopRatedMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repository.getTopRatedMovies(token)
                repository.loadData.postValue(false)
            }catch (throwable: Throwable){
                repository.loadData.postValue(false)
                throwableCase(throwable)
            }
        }
    }

    /**
     * Parse throwable response into repository value
     *
     * Example Output : HTTP error response 404 please retry
     */
    private fun throwableCase(throwable: Throwable){
        when(throwable){
            is IOException -> repository.movies
                .postValue(ResultOfNetwork.Failure("[IO] error ${throwable.message} please retry", throwable))
            is HttpException -> {
                repository.movies
                    .postValue(ResultOfNetwork.Failure("[HTTP] error ${throwable.message} please retry", throwable))
            }
            else -> repository.movies
                .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
        }
    }
}