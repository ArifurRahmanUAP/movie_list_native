package com.arif.movielistnative

import com.arif.movielistnative.dataBase.AppDao
import com.arif.movielistnative.dataBase.AppTable
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiService: ApiService,
    private val appDao: AppDao
) {
    suspend fun getPopularMovies(pageNum: Int) = apiService.getPopularMovie(pageNum)

    suspend fun getNowShowingMovies(pageNum: Int) = apiService.getNowShowingMovie(pageNum)

    suspend fun getMovieDetailsFromId(id: Int) = apiService.getMovieDetailsById(id)

    suspend fun addToBookmark(appTable: AppTable) = appDao.addBookmark(appTable)

    suspend fun getBookmarksMovie() = appDao.getAllBookmarks()
    suspend fun isBookmarkExist(id: Int) = appDao.checkIfExit(id)

    suspend fun deleteFromBookmark(id: Int?) = appDao.deleteBookmark(id)

}