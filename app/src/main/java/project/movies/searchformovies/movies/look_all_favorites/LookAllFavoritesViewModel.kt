package project.movies.searchformovies.movies.look_all_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import project.movies.searchformovies.movies.movies_main.MoviesRepository
import project.movies.searchformovies.remote.MoviesData
import timber.log.Timber

class LookAllFavoritesViewModel : ViewModel() {

    private val repository = MoviesRepository()

    private val _favoritesMoviesStateFlow: MutableStateFlow<List<MoviesData>> =
        MutableStateFlow(listOf())
    val favoritesMoviesStateFlow: StateFlow<List<MoviesData>>
        get() = _favoritesMoviesStateFlow


    fun getAllFavoritesMovie() {
        viewModelScope.launch {
            try {
                _favoritesMoviesStateFlow.value = repository.getAllFavoritesMovie()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    fun removeFavoritesMovie(favoriteId: Int) {
        viewModelScope.launch {
            try {
                repository.removeFavoritesMovie(favoriteId)
                getAllFavoritesMovie()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}