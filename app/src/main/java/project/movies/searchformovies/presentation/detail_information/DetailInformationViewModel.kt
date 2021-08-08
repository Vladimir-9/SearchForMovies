package project.movies.searchformovies.presentation.detail_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import project.movies.searchformovies.data.MoviesRepository
import project.movies.searchformovies.remote.MoviesData
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailInformationViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    fun saveFavoritesMovie(favoritesMovie: MoviesData) {
        viewModelScope.launch {
            try {
                repository.saveFavoritesMovie(favoritesMovie)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }


}