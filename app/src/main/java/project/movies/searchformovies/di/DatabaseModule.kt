package project.movies.searchformovies.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import project.movies.searchformovies.data.local.MoviesDao
import project.movies.searchformovies.data.local.MoviesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDataBase(application: Application): MoviesDatabase {
        return Room.databaseBuilder(
            application,
            MoviesDatabase::class.java,
            MoviesDatabase.DB_NAME
        ).build()
    }

    @Provides
    fun providesMoviesDao(db: MoviesDatabase): MoviesDao {
        return db.moviesDao()
    }
}