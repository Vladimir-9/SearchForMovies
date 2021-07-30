package project.movies.searchformovies

import android.app.Application
import timber.log.Timber

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}