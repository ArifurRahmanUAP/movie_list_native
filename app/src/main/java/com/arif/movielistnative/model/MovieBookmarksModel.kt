
import com.squareup.moshi.Json

data class MovieBookmarksModel(

    @field:Json(name = "title")
    val title: String? = null,

    @field:Json(name = "backdrop_path")
    val backdropPath: String? = null,

    @field:Json(name = "genres")
    val genres: String? = null,

    @field:Json(name = "id")
    val id: Int? = null,

    @field:Json(name = "overview")
    val overview: String? = null,

    @field:Json(name = "original_title")
    val originalTitle: String? = null,

    @field:Json(name = "runtime")
    val runtime: Int? = null,

    @field:Json(name = "poster_path")
    val posterPath: String? = null,


    @field:Json(name = "vote_average")
    val voteAverage: Double? = null,

    )
