package com.arif.movielistnative.Genres


data class GenresModel(
    val genres: List<Genre>
)
data class Genre (
    val id: Int,

    val name: String
)
