package project.movies.searchformovies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.movies.searchformovies.domain.model.MoviesContract

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: MoviesEntity)

    @Query("SELECT * FROM ${MoviesContract.TABLE_NAME}")
    suspend fun getAllMovies(): List<MoviesEntity>

    @Query("DELETE FROM ${MoviesContract.TABLE_NAME} WHERE ${MoviesContract.Columns.ID} = :favoriteId")
    suspend fun removeFavoriteById(favoriteId: Int)
}