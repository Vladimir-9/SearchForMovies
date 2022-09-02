package project.movies.searchformovies.data.mapper

import project.movies.searchformovies.data.local.MoviesEntity
import project.movies.searchformovies.data.remote.dto.MoviesDataDto
import project.movies.searchformovies.data.remote.dto.RemoteMoviesDto
import project.movies.searchformovies.domain.model.MoviesData
import project.movies.searchformovies.domain.model.RemoteMovies
import project.movies.searchformovies.utility.orZero

fun RemoteMoviesDto.toRemoteMovies() = RemoteMovies(
    page = this.page,
    results = this.results?.map { it.toMoviesData() }.orEmpty()
)

fun MoviesDataDto.toMoviesData() = MoviesData(
    id = this.id.orZero(),
    description = this.description.orEmpty(),
    posterPath = this.posterPath.orEmpty(),
    backdropPath = this.backdropPath.orEmpty(),
    releaseDate = this.releaseDate.orEmpty(),
    title = this.title.orEmpty()
)

fun MoviesEntity.toMoviesData() = MoviesData(
    id = this.id.orZero(),
    description = this.description,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate,
    title = this.title
)

fun MoviesData.toMoviesEntity() = MoviesEntity(
    id = this.id.orZero(),
    description = this.description,
    posterPath = this.posterPath,
    backdropPath = this.backdropPath,
    releaseDate = this.releaseDate,
    title = this.title
)
