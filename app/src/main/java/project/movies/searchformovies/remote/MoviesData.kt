package project.movies.searchformovies.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMovies(
    val page: Int,
    val results: List<MoviesData>,
    @Json(name = "total_pages")
    val totalPages: Int
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