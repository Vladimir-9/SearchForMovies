package project.movies.searchformovies.data

import project.movies.searchformovies.data.local.MoviesDao
import project.movies.searchformovies.data.local.MoviesEntity
import project.movies.searchformovies.data.mapper.toMoviesData
import project.movies.searchformovies.data.remote.NetworkingApi
import project.movies.searchformovies.domain.model.MoviesData
import project.movies.searchformovies.domain.repositories.MoviesRepository
import project.movies.searchformovies.utility.toResource
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val favoritesMovieDao: MoviesDao,
    private val networkingApi: NetworkingApi
) : MoviesRepository {

    override suspend fun searchPopularMovies() = networkingApi.popularMovies().toResource {
        it?.results?.map { movies -> movies.toMoviesData() }.orEmpty()
    }

    override suspend fun searchMovies(searchResponse: String) = networkingApi.searchMovies(query = searchResponse).toResource { movies->
            movies?.results?.map { it.toMoviesData() }.orEmpty()
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