package com.arif.movielistnative.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arif.movielistnative.model.NowShowingMovieResponseModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.arif.movielistnative.AppRepository
import com.arif.movielistnative.PopularMovieResponseModel
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiRepository: AppRepository) : ViewModel() {

    private val _nowShowingMoviesLiveData = MutableLiveData<NowShowingMovieResponseModel?>()
    val nowShowingList: LiveData<NowShowingMovieResponseModel?> get() = _nowShowingMoviesLiveData

    private val _popularMoviesLiveData = MutableLiveData<PopularMovieResponseModel?>()
    val popularMovieList: LiveData<PopularMovieResponseModel?> get() = _popularMoviesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData


    fun callNowShowingMovieList(pageNum: Int) {
        viewModelScope.launch {

            when (val response = apiRepository.getNowShowingMovies(pageNum)) {
                is NetworkResponse.Success -> {
                    _nowShowingMoviesLiveData.value = response.body
                }
                is NetworkResponse.ServerError -> {
                    _errorLiveData.value = response.body?.statusMessage ?: ("Something Went Wrong")

                }
                is NetworkResponse.NetworkError -> {
                    _errorLiveData.value = "No Internet Connection"
                }
                is NetworkResponse.UnknownError -> {
                    _errorLiveData.value = "SomeThing Went Wrong"
                }
            }

        }

    }

    fun callPopularMovieList(pageNum: Int) {
        viewModelScope.launch {

            when (val response = apiRepository.getPopularMovies(pageNum)) {
                is NetworkResponse.Success -> {
                    _popularMoviesLiveData.value = response.body
                }
                is NetworkResponse.ServerError -> {
                    _errorLiveData.value = response.body?.statusMessage ?: ("Something Went Wrong")

                }
                is NetworkResponse.NetworkError -> {
                    _errorLiveData.value = "No Internet Connection"
                }
                is NetworkResponse.UnknownError -> {
                    _errorLiveData.value = "SomeThing Went Wrong"
                }
            }

        }

    }


}