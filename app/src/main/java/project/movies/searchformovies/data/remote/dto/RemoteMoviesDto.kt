package project.movies.searchformovies.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteMoviesDto(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<MoviesDataDto>?
)

@JsonClass(generateAdapter = true)
data class MoviesDataDto(
    val id: Int?,
    @Json(name = "overview")
    val description: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "title")
    val title: String?
)