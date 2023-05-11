package project.movies.searchformovies.data

import project.movies.searchformovies.data.local.MoviesDao
import project.movies.searchformovies.data.local.DrinksEntity
import project.movies.searchformovies.data.mapper.toDataDetailsDrink
import project.movies.searchformovies.data.mapper.toDetailsDrink
import project.movies.searchformovies.data.mapper.toDrinksData
import project.movies.searchformovies.data.mapper.toMoviesData
import project.movies.searchformovies.data.remote.NetworkingApi
import project.movies.searchformovies.data.remote.dto.DrinksDto
import project.movies.searchformovies.domain.model.DataDetailsDrink
import project.movies.searchformovies.domain.model.DrinksData
import project.movies.searchformovies.domain.repositories.DrinksRepository
import project.movies.searchformovies.utility.Resource
import project.movies.searchformovies.utility.toResource
import javax.inject.Inject

class DrinksRepositoryImpl @Inject constructor(
    private val favoritesMovieDao: MoviesDao,
    private val networkingApi: NetworkingApi
) : DrinksRepository {

    override suspend fun alcoholicDrinks() = networkingApi.alcoholicDrinks().toResource {
        it?.drinks?.map { drinks -> drinks.toDrinksData() }.orEmpty()
    }

    override suspend fun saveFavoritesMovie(favoritesMovie: DrinksEntity) {
        favoritesMovieDao.insertMovies(favoritesMovie)
    }

    override suspend fun getAllFavoritesMovie(): List<DrinksData> {
        val moviesEntity = favoritesMovieDao.getAllMovies()
        return moviesEntity.map { it.toMoviesData() }
    }

    override suspend fun removeFavoritesMovie(favoriteId: String) {
        favoritesMovieDao.removeFavoriteById(favoriteId)
    }

    override suspend fun detailDesc(id: String) = networkingApi.detailDesc(id).toResource {
        it?.drinks?.map { detail -> detail.toDataDetailsDrink() }.orEmpty()
    }

    override suspend fun searchDrinks(searchQuery: String): Resource<List<DrinksData>> =
        networkingApi.searchDrinks(searchQuery).toResource { drink ->
            drink?.drinks?.map { it.toDrinksData() }.orEmpty()
        }
}