package project.movies.searchformovies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import project.movies.searchformovies.domain.repositories.MoviesRepository
import project.movies.searchformovies.data.MoviesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Repository {

    @Binds
    @Singleton
    abstract fun provideRepository(impl: MoviesRepositoryImpl): MoviesRepository
}