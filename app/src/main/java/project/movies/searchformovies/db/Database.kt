package project.movies.searchformovies.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: MoviesDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            MoviesDatabase.DB_NAME
        ).build()
    }
}