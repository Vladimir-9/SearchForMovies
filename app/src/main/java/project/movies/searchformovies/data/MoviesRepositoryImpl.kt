package project.movies.searchformovies.data

import project.movies.searchformovies.data.db.MoviesDao
import project.movies.searchformovies.presentation.movies_main.MoviesLoadState
import project.movies.searchformovies.remote.MoviesData
import project.movies.searchformovies.remote.api.NetworkingApi
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val favoritesMovieDao: MoviesDao,
    private val networkingApi: NetworkingApi
) : MoviesRepository {

    override suspend fun searchPopularMovies(): MoviesLoadState {
        return try {
            MoviesLoadState.Success(networkingApi.popularMovies().results)
        } catch (e: Exception) {
            MoviesLoadState.Error("error")
        }
    }

    override suspend fun searchMovies(searchResponse: String): MoviesLoadState {
        return try {
            MoviesLoadState.Success(networkingApi.searchMovies(query = searchResponse).results)
        } catch (e: Exception) {
            MoviesLoadState.Error("error")
        }
    }

    override suspend fun saveFavoritesMovie(favoritesMovie: MoviesData) {
        favoritesMovieDao.insertMovies(favoritesMovie)
    }

    override suspend fun getAllFavoritesMovie(): List<MoviesData> {
        return favoritesMovieDao.getAllMovies()
    }

    override suspend fun removeFavoritesMovie(favoriteId: Int) {
        favoritesMovieDao.removeFavoriteById(favoriteId)
    }
}