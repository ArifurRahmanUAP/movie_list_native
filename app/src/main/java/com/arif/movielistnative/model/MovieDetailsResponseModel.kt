package com.arif.movielistnative.model

import com.google.gson.annotations.SerializedName
data class MovieDetailsResponseModel(

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("genres")
    val genres: List<GenresItem?>? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguagesItem?>? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("adult")
    val adult: Boolean? = null,

    var isBookmarked: Boolean = false
)

data class GenresItem(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int? = null
)

data class SpokenLanguagesItem(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("iso_639_1")
    val iso6391: String? = null,

    @SerializedName("english_name")
    val englishName: String? = null
)
