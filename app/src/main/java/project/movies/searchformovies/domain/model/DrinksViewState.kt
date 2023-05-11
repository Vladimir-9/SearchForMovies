package project.movies.searchformovies.domain.model

data class DrinksViewState(
    val listDrinks: List<DrinksData> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)