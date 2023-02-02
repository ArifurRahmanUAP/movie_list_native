package com.arif.movielistnative

import com.arif.movielistnative.dataBase.AppDao
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiService: ApiService,
//    private val appDao: AppDao
) {

    suspend fun getPopularMovies() = apiService.getPopularMovie()

    suspend fun getNowShowingMovies() = apiService.getNowShowingMovie()

    suspend fun getMovieDetailsFromId(id: Int) = apiService.getMovieDetailsById(id)

    suspend fun addToBookmark() = apiService.getNowShowingMovie()

    suspend fun deleteFromBookmark() = apiService.getNowShowingMovie()

    suspend fun isBookmarkExist() = apiService.getNowShowingMovie()

}