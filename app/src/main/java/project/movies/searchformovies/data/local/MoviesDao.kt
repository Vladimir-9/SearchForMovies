package project.movies.searchformovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.movies.searchformovies.domain.model.DrinksContract

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: DrinksEntity)

    @Query("SELECT * FROM ${DrinksContract.TABLE_NAME}")
    suspend fun getAllMovies(): List<DrinksEntity>

    @Query("DELETE FROM ${DrinksContract.TABLE_NAME} WHERE ${DrinksContract.Columns.ID} = :favoriteId")
    suspend fun removeFavoriteById(favoriteId: String)
}