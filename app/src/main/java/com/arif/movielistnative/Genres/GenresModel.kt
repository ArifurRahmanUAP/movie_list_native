package com.arif.movielistnative.Genres

import com.arif.movielistnative.model.GenresItem
import com.google.gson.annotations.SerializedName


data class GenresModel(
    @SerializedName("genres")
    val genres: List<GenresItem?>? = null,
)
