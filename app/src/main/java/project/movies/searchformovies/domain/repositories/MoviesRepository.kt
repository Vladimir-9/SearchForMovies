package project.movies.searchformovies.domain.repositories

import project.movies.searchformovies.data.local.MoviesEntity
import project.movies.searchformovies.domain.model.MoviesData

interface MoviesRepository {
    suspend fun searchPopularMovies(): List<MoviesData>
    suspend fun searchMovies(searchResponse: String): List<MoviesData>
    suspend fun saveFavoritesMovie(favoritesMovie: MoviesEntity)
    suspend fun getAllFavoritesMovie(): List<MoviesData>
    suspend fun removeFavoritesMovie(favoriteId: Int)
}