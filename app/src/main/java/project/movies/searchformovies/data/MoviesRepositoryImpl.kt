package project.movies.searchformovies.data

import project.movies.searchformovies.data.local.MoviesDao
import project.movies.searchformovies.data.local.MoviesEntity
import project.movies.searchformovies.data.mapper.toMoviesData
import project.movies.searchformovies.data.remote.NetworkingApi
import project.movies.searchformovies.domain.model.MoviesData
import project.movies.searchformovies.domain.repositories.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val favoritesMovieDao: MoviesDao,
    private val networkingApi: NetworkingApi
) : MoviesRepository {

    override suspend fun searchPopularMovies(): List<MoviesData> {
        return try {
            val movies = networkingApi.popularMovies().results
            movies?.map { it.toMoviesData() }.orEmpty()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun searchMovies(searchResponse: String): List<MoviesData> {
        return try {
            val movies = networkingApi.searchMovies(query = searchResponse).results
            movies?.map { it.toMoviesData() }.orEmpty()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun saveFavoritesMovie(favoritesMovie: MoviesEntity) {
        favoritesMovieDao.insertMovies(favoritesMovie)
    }

    override suspend fun getAllFavoritesMovie(): List<MoviesData> {
        val moviesEntity = favoritesMovieDao.getAllMovies()
        return moviesEntity.map { it.toMoviesData() }
    }

    override suspend fun removeFavoritesMovie(favoriteId: Int) {
        favoritesMovieDao.removeFavoriteById(favoriteId)
    }
}