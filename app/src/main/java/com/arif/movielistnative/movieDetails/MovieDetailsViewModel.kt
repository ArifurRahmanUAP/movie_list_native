package com.arif.movielistnative.movieDetails

import MovieDetailsResponseModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arif.movielistnative.AppRepository
import com.arif.movielistnative.dataBase.AppTable
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val apiRepository: AppRepository) :
    ViewModel() {

    private val _detailsMovieLiveData = MutableLiveData<MovieDetailsResponseModel?>()
    val detailesMovieLiveData: LiveData<MovieDetailsResponseModel?>
        get() = _detailsMovieLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun callMovieDetails(id: Int) {

        viewModelScope.launch {

            when (val response = apiRepository.getMovieDetailsFromId(id)) {

                is NetworkResponse.Success -> {
                    val output = response.body
                    output.isBookmarked = apiRepository.isBookmarkExist(id)
                    _detailsMovieLiveData.value = output
                }
                is NetworkResponse.ServerError -> {
                    _errorLiveData.value = response.body?.statusMessage ?: ("something went wrong")
                }
                is NetworkResponse.NetworkError -> {
                    _errorLiveData.value = "No internet"
                }
                is NetworkResponse.UnknownError -> {
                    _errorLiveData.value = "something went wrong"
                }
            }
        }
    }

    fun addBookmarks(appTable: AppTable) {
        viewModelScope.launch { apiRepository.addToBookmark(appTable) }
    }
    fun deleteBookmarks(id: Int?) {
        viewModelScope.launch { apiRepository.deleteFromBookmark(id) }
    }

}