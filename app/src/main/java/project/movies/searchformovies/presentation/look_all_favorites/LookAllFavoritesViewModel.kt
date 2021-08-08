package project.movies.searchformovies.presentation.look_all_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import project.movies.searchformovies.data.MoviesRepository
import project.movies.searchformovies.remote.MoviesData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LookAllFavoritesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _favoritesMoviesStateFlow: MutableStateFlow<List<MoviesData>> =
        MutableStateFlow(listOf())
    val favoritesMoviesStateFlow: StateFlow<List<MoviesData>> =
        _favoritesMoviesStateFlow.asStateFlow()


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