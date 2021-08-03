package project.movies.searchformovies.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import project.movies.searchformovies.db.models.MoviesContract
import project.movies.searchformovies.remote.MoviesData

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: MoviesData)

    @Query("SELECT * FROM ${MoviesContract.TABLE_NAME}")
    suspend fun getAllMovies(): List<MoviesData>

    @Query("DELETE FROM ${MoviesContract.TABLE_NAME} WHERE ${MoviesContract.Columns.ID} = :favoriteId")
    suspend fun removeFavoriteById(favoriteId: Int)
}