package com.arif.movielistnative

import MovieDetailsResponseModel
import com.arif.movielistnative.model.NowShowingMovieResponseModel
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/now_playing?api_key=98f3908014410fc8a0a0393df1b060af&language=en-US")
    suspend fun getNowShowingMovie(@Query ("page") pageNum: Int): NetworkResponse<NowShowingMovieResponseModel, ErrorResponse>
//    @GET("3/movie/now_playing?api_key=98f3908014410fc8a0a0393df1b060af&language=en-US&page={pageNum}")
//    suspend fun getNowShowingMovie(@Path ("pageNum") pageNum: Int): NetworkResponse<NowShowingMovieResponseModel, ErrorResponse>


    @GET("3/movie/popular?api_key=98f3908014410fc8a0a0393df1b060af&language=en-US")
    suspend fun getPopularMovie(@Query ("page") pageNum: Int): NetworkResponse<PopularMovieResponseModel, ErrorResponse>

    @GET("/3/movie/{movieId}?api_key=98f3908014410fc8a0a0393df1b060af&language=en-US")
    suspend fun getMovieDetailsById(@Path ("movieId") id: Int): NetworkResponse<MovieDetailsResponseModel, ErrorResponse>



}