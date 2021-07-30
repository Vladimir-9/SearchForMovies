package project.movies.searchformovies.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovies(
    val page: Int,
    val results: List<MoviesData>
)

@JsonClass(generateAdapter = true)
data class MoviesData(
    val id: Int,
    @Json(name = "overview")
    val description: String,
    val poster_path: String?,
    val release_date: String?,
    val title: String
)