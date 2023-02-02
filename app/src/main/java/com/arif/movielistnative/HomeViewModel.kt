package com.arif.movielistnative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arif.movielistnative.model.NowShowingMovieResponseModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiRepository: AppRepository) : ViewModel() {

    private val _nowShowingMoviesLiveData = MutableLiveData<NowShowingMovieResponseModel?>()
    val nowShowingMovieListLiveData: LiveData<NowShowingMovieResponseModel?> get() = _nowShowingMoviesLiveData

    private val _popularMoviesLiveData = MutableLiveData<PopularMovieResponseModel?>()
    val popularMovieList: LiveData<PopularMovieResponseModel?> get() = _popularMoviesLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData


    fun callNowShowingMovieList() {
        viewModelScope.launch {

            when (val response = apiRepository.getNowShowingMovies()) {
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

    fun callPopularMovieList() {
        viewModelScope.launch {

            when (val response = apiRepository.getPopularMovies()) {
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