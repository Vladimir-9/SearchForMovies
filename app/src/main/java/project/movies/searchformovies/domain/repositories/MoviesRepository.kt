package project.movies.searchformovies.domain.repositories

import project.movies.searchformovies.data.local.MoviesEntity
import project.movies.searchformovies.domain.model.MoviesData
import project.movies.searchformovies.utility.Resource

interface MoviesRepository {
    suspend fun searchPopularMovies(): Resource<List<MoviesData>>
    suspend fun searchMovies(searchResponse: String): Resource<List<MoviesData>>
    suspend fun saveFavoritesMovie(favoritesMovie: MoviesEntity)
    suspend fun getAllFavoritesMovie(): List<MoviesData>
    suspend fun removeFavoritesMovie(favoriteId: Int)
}