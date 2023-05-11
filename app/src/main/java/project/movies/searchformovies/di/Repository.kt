package project.movies.searchformovies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import project.movies.searchformovies.domain.repositories.DrinksRepository
import project.movies.searchformovies.data.DrinksRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Repository {

    @Binds
    @Singleton
    abstract fun provideRepository(impl: DrinksRepositoryImpl): DrinksRepository
}