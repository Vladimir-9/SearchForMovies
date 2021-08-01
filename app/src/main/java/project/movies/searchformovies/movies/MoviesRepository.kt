package project.movies.searchformovies.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import project.movies.searchformovies.networking.EverythingMoviesPagingSource
import project.movies.searchformovies.networking.Networking
import project.movies.searchformovies.remote.MoviesData

class MoviesRepository {

    fun searchPopularMovies( ): MoviesLoadState {
        return MoviesLoadState.Success(pagerMovies())
    }

//    suspend fun searchMovies(context: CoroutineContext, searchResponse: String): MoviesLoadState {
//        return withContext(context) {
//            try {
//                MoviesLoadState.Success(Networking.networkingApi.searchMovies(query = searchResponse))
//            } catch (e: Exception) {
//                MoviesLoadState.Error(e.message)
//            }
//        }
//    }

     fun pagerMovies(): Flow<PagingData<MoviesData>> {
        return Pager(PagingConfig(1, enablePlaceholders = false), pagingSourceFactory = {
            EverythingMoviesPagingSource(Networking.networkingApi)
        }).flow
    }
}