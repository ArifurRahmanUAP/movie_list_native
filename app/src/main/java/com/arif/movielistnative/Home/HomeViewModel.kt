package com.arif.movielistnative.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.arif.movielistnative.Genres.GenresModel
import com.arif.movielistnative.api.AppRepository
import com.arif.movielistnative.dataBase.GenresTable
import com.arif.movielistnative.model.NowShowingMovieResponseModel
import com.arif.movielistnative.model.PopularMovieResponseModel
import com.arif.movielistnative.model.Results
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

    private val _genresListLiveData = MutableLiveData<GenresModel?>()
    val genresListLiveData: LiveData<GenresModel?> get() = _genresListLiveData

    private val _genresNameById = MutableLiveData<List<GenresTable>>()
    val genresNameById: LiveData<List<GenresTable>> get() = _genresNameById


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

    fun callGenresList() {
        viewModelScope.launch {

            when (val response = apiRepository.getGenresList()) {
                is NetworkResponse.Success -> {
                    _genresListLiveData.value = response.body
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
                    Log.d("dataDebug","${response.body}")
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
    fun addGenres(genresTable: GenresTable) {
        viewModelScope.launch { apiRepository.addGenres(genresTable) }
    }

    fun getGenresNameById() {
        viewModelScope.launch {
            val res = apiRepository.getGenresNameById()
            _genresNameById.value = res
        }
    }



}