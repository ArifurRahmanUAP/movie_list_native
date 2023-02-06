package com.arif.movielistnative.api

import com.arif.movielistnative.api.ApiService
import com.arif.movielistnative.dataBase.AppDao
import com.arif.movielistnative.dataBase.AppTable
import com.arif.movielistnative.dataBase.GenresTable
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiService: ApiService,
    private val appDao: AppDao
) {
    suspend fun getPopularMovies(pageNum: Int) = apiService.getPopularMovie(pageNum)

    suspend fun getNowShowingMovies(pageNum: Int) = apiService.getNowShowingMovie(pageNum)

    suspend fun getMovieDetailsFromId(id: Int) = apiService.getMovieDetailsById(id)
    suspend fun getGenresList() = apiService.getGenres()

    suspend fun addToBookmark(appTable: AppTable) = appDao.addBookmark(appTable)
    suspend fun addGenres(genresTable: GenresTable) = appDao.addGenres(genresTable)

    suspend fun getBookmarksMovie() = appDao.getAllBookmarks()
    suspend fun isBookmarkExist(id: Int) = appDao.checkIfExit(id)

    suspend fun deleteFromBookmark(id: Int?) = appDao.deleteBookmark(id)

}