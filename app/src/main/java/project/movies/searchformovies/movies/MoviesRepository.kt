package project.movies.searchformovies.movies

import kotlinx.coroutines.withContext
import project.movies.searchformovies.networking.Networking
import kotlin.coroutines.CoroutineContext

class MoviesRepository {

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
}