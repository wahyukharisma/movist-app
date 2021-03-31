package com.example.movist.presentation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movist.repository.MovieRepository
import com.example.movist.services.model.detail.Detail
import com.example.movist.services.model.review.Review
import com.example.movist.util.ResultOfNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
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
    @Named("token") private val token : String
) : ViewModel(){
    val detail : LiveData<ResultOfNetwork<Detail>>
    val review : LiveData<ResultOfNetwork<Review>>
    val loadData : LiveData<Boolean>

    init {
        this.detail = repository.detail
        this.review = repository.review
        this.loadData = repository.loadData
    }

    /**
     * Get detail of movie selected
     *
     * Output : Result of response [Detail] object handle using sealed class
     *
     * @param id movie id
     */
    fun getDetail(id : Int){
        viewModelScope.launch {
            try{
                repository.getDetailMovie(token, id)
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
        viewModelScope.launch {
            try{
                repository.getReviewMovie(token, id)
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