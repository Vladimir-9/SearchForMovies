package project.movies.searchformovies.movies

import kotlinx.coroutines.withContext
import project.movies.searchformovies.db.Database
import project.movies.searchformovies.networking.Networking
import project.movies.searchformovies.remote.MoviesData
import kotlin.coroutines.CoroutineContext

class MoviesRepository {

    private val favoritesMovieDao = Database.instance.moviesDao()

    suspend fun searchPopularMovies(context: CoroutineContext): MoviesLoadState {
        return withContext(context) {
            try {
                MoviesLoadState.Success(Networking.networkingApi.popularMovies().results)
            } catch (e: Exception) {
                MoviesLoadState.Error(e.message)
            }
        }
    }

    suspend fun searchMovies(context: CoroutineContext, searchResponse: String): MoviesLoadState {
        return withContext(context) {
            try {
                MoviesLoadState.Success(Networking.networkingApi.searchMovies(query = searchResponse).results)
            } catch (e: Exception) {
                MoviesLoadState.Error(e.message)
            }
        }
    }

    suspend fun saveFavoritesMovie(favoritesMovie: MoviesData){
        favoritesMovieDao.insertMovies(favoritesMovie)
    }

    suspend fun getAllFavoritesMovie(): List<MoviesData>{
        return favoritesMovieDao.getAllMovies()
    }

    suspend fun removeFavoritesMovie(favoriteId: Int){
        favoritesMovieDao.removeFavoriteById(favoriteId)
    }
}