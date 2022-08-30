package project.movies.searchformovies.domain.model

data class MoviesViewState(
    val listMovies: List<MoviesData> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)