package project.movies.searchformovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import project.movies.searchformovies.data.local.MoviesDatabase.Companion.DB_VERSION

@Database(
    entities = [MoviesEntity::class],
    version = DB_VERSION
)

abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "movies_database"
    }
}