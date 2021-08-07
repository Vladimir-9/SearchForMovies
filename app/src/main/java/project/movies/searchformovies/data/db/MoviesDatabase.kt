package project.movies.searchformovies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import project.movies.searchformovies.data.db.MoviesDatabase.Companion.DB_VERSION
import project.movies.searchformovies.remote.MoviesData

@Database(
    entities = [
        MoviesData::class
    ], version = DB_VERSION
)

abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "movies_database"
    }
}