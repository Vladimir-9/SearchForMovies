package project.movies.searchformovies.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class RemoteMovies(
    val page: Int,
    val results: List<MoviesData>
)

@Parcelize
data class MoviesData(
    val id: Int,
    val description: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val title: String
) : Parcelable