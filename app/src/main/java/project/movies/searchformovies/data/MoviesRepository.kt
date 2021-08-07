package project.movies.searchformovies.data

import project.movies.searchformovies.presentation.movies_main.MoviesLoadState
import project.movies.searchformovies.remote.MoviesData
import kotlin.coroutines.CoroutineContext

interface MoviesRepository {
    suspend fun searchPopularMovies(context: CoroutineContext): MoviesLoadState
    suspend fun searchMovies(context: CoroutineContext, searchResponse: String): MoviesLoadState
    suspend fun saveFavoritesMovie(favoritesMovie: MoviesData)
    suspend fun getAllFavoritesMovie(): List<MoviesData>
    suspend fun removeFavoritesMovie(favoriteId: Int)
}