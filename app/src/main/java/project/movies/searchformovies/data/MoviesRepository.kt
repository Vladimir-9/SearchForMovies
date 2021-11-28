package project.movies.searchformovies.data

import io.reactivex.Single
import project.movies.searchformovies.remote.MoviesData

interface MoviesRepository {
    fun searchPopularMovies(): Single<List<MoviesData>>
    suspend fun searchMovies(searchResponse: String): List<MoviesData>
    suspend fun saveFavoritesMovie(favoritesMovie: MoviesData)
    suspend fun getAllFavoritesMovie(): List<MoviesData>
    suspend fun removeFavoritesMovie(favoriteId: Int)
}