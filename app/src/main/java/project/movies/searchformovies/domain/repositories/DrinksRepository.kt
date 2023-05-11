package project.movies.searchformovies.domain.repositories

import project.movies.searchformovies.data.local.DrinksEntity
import project.movies.searchformovies.domain.model.DataDetailsDrink
import project.movies.searchformovies.domain.model.DrinksData
import project.movies.searchformovies.utility.Resource

interface DrinksRepository {
    suspend fun alcoholicDrinks(): Resource<List<DrinksData>>
    suspend fun saveFavoritesMovie(favoritesMovie: DrinksEntity)
    suspend fun getAllFavoritesMovie(): List<DrinksData>
    suspend fun removeFavoritesMovie(favoriteId: String)
    suspend fun detailDesc(id: String): Resource<List<DataDetailsDrink>>
}