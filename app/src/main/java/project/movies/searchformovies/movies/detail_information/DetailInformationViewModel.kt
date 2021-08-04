package project.movies.searchformovies.movies.detail_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.movies.searchformovies.movies.movies_main.MoviesRepository
import project.movies.searchformovies.remote.MoviesData
import timber.log.Timber

class DetailInformationViewModel : ViewModel() {

    private var repository = MoviesRepository()

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