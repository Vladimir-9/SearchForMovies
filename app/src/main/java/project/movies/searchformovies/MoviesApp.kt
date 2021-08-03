package project.movies.searchformovies

import android.app.Application
import project.movies.searchformovies.db.Database
import timber.log.Timber

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Database.init(this)
    }
}