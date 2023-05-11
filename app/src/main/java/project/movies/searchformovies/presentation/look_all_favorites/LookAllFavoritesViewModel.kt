package project.movies.searchformovies.presentation.look_all_favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import project.movies.searchformovies.domain.model.DrinksData
import project.movies.searchformovies.domain.repositories.DrinksRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LookAllFavoritesViewModel @Inject constructor(
    private val repository: DrinksRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    private val _favoritesMovies = MutableLiveData<List<DrinksData>>()
    val favoritesMovies: LiveData<List<DrinksData>> = _favoritesMovies

    fun getAllFavoritesMovie() {
        viewModelScope.launch(exceptionHandler) {
            _favoritesMovies.postValue(repository.getAllFavoritesMovie())
        }
    }

    fun removeFavoritesMovie(favoriteId: String) {
        viewModelScope.launch(exceptionHandler) {
            repository.removeFavoritesMovie(favoriteId)
            getAllFavoritesMovie()
        }
    }
}