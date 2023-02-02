package com.arif.movielistnative

import com.arif.movielistnative.model.NowShowingMovieResponseModel
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("3/movie/now_playing?api_key=98f3908014410fc8a0a0393df1b060af&language=en-US&page=1")
    suspend fun getNowShowingMovie(): NetworkResponse<NowShowingMovieResponseModel, ErrorResponse>

    @GET("3/movie/popular?api_key=98f3908014410fc8a0a0393df1b060af&language=en-US&page=1")
    suspend fun getPopularMovie(): NetworkResponse<PopularMovieResponseModel, ErrorResponse>

    @GET("/3/movie/{movieId}?api_key=98f3908014410fc8a0a0393df1b060af&language=en-US")
    suspend fun getMovieDetailsById(@Path ("movieId") id: Int): NetworkResponse<PopularMovieResponseModel, ErrorResponse>



}