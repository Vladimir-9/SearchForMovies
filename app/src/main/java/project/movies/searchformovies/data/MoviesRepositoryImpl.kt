package project.movies.searchformovies.data

import io.reactivex.Single
import project.movies.searchformovies.data.db.MoviesDao
import project.movies.searchformovies.remote.MoviesData
import project.movies.searchformovies.remote.api.NetworkingApi
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val favoritesMovieDao: MoviesDao,
    private val networkingApi: NetworkingApi
) : MoviesRepository {

    override fun searchPopularMovies(): Single<List<MoviesData>> =
        Single.create {
            val call = networkingApi.popularMovies().execute()
            if (call.isSuccessful) {
                val listMovies = call.body()?.results
                it.onSuccess(listMovies ?: emptyList())
            }
        }

    override suspend fun searchMovies(searchResponse: String): List<MoviesData> {
        return try {
            networkingApi.searchMovies(query = searchResponse).results
        } catch (e: Exception) {
            emptyList()
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