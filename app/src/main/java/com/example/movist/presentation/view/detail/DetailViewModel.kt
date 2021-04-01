package com.example.movist.presentation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movist.repository.MovieFavoriteRepository
import com.example.movist.repository.MovieRepository
import com.example.movist.services.model.detail.Detail
import com.example.movist.services.model.review.Review
import com.example.movist.services.storage.entities.MovieFavorite
import com.example.movist.util.ResultOfNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val repository : MovieRepository,
    private val repositoryFav : MovieFavoriteRepository,
    @Named("token") private val token : String
) : ViewModel(){
    val detail : LiveData<ResultOfNetwork<Detail>>
    val review : LiveData<ResultOfNetwork<Review>>

    val movie: LiveData<MovieFavorite> get() = mutableMovie
    private val mutableMovie = MutableLiveData<MovieFavorite>()

    val movieSaved: LiveData<Boolean> get() = mutableMovieSaved
    private val mutableMovieSaved = MutableLiveData<Boolean>()

    val movieRemove: LiveData<Boolean> get() = mutableMovieRemoved
    private val mutableMovieRemoved = MutableLiveData<Boolean>()

    init {
        this.detail = repository.detail
        this.review = repository.review
    }

    /**
     * GEt favorite movie by movie id
     *
     * @param movieId id of movie
     */
    fun getMovieById(movieId : Int){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                mutableMovie.postValue(repositoryFav.getMovieById(movieId))
            }catch (throwable : Throwable){ }
        }
    }

    /**
     * Remove favorite movie from local database
     *
     * @param data movie id
     */
    fun removeFavMovie(data : MovieFavorite){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repositoryFav.removeMovie(data)
                mutableMovieRemoved.postValue(true)
            }catch (throwable : Throwable){
                mutableMovieRemoved.postValue(false)
            }
        }
    }

    /**
     * Insert favorite movie into local database
     *
     * @param movie movie entities object
     */
    fun insertFavMovie(movie : MovieFavorite){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repositoryFav.insertMovie(movie)
                mutableMovieSaved.postValue(true)
            }catch (throwable : Throwable){
                mutableMovieSaved.postValue(false)
            }
        }
    }

    /**
     * Get detail of movie selected
     *
     * Output : Result of response [Detail] object handle using sealed class
     *
     * @param id movie id
     */
    fun getDetail(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repository.getDetail(token, id)
                repository.loadData.postValue(false)
            }catch (throwable: Throwable){
                repository.loadData.postValue(false)
                when(throwable){
                    is IOException -> repository.detail
                        .postValue(ResultOfNetwork.Failure("[IO] error ${throwable.message} please retry", throwable))
                    is HttpException -> {
                        repository.detail
                            .postValue(ResultOfNetwork.Failure("[HTTP] error ${throwable.message} please retry", throwable))
                    }
                    else -> repository.detail
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }

    /**
     * Get review of movie selected
     *
     * Output : Result of response [Review] object handle using sealed class
     *
     * @param id : movie id
     */
    fun getReview(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                repository.getReview(token, id)
                repository.loadData.postValue(false)
            }catch (throwable: Throwable){
                repository.loadData.postValue(false)
                when(throwable){
                    is IOException -> repository.review
                        .postValue(ResultOfNetwork.Failure("[IO] error ${throwable.message} please retry", throwable))
                    is HttpException -> {
                        repository.review
                            .postValue(ResultOfNetwork.Failure("[HTTP] error ${throwable.message} please retry", throwable))
                    }
                    else -> repository.review
                        .postValue(ResultOfNetwork.Failure("[Unknown] error ${throwable.message} please retry", throwable))
                }
            }
        }
    }
}