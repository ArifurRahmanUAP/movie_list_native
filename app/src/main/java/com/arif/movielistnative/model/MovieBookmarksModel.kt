package com.arif.movielistnative.model

import com.google.gson.annotations.SerializedName

data class MovieBookmarksModel(

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("genres")
    val genres: String? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,


    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    )
