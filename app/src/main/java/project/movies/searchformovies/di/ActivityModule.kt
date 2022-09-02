package project.movies.searchformovies.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import project.movies.searchformovies.utility.ConnectionStateMonitor

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun providesConnectionState(application: Application) : ConnectionStateMonitor {
        return ConnectionStateMonitor(application)
    }
}