package project.movies.searchformovies.presentation.movies_main.detail_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import project.movies.searchformovies.data.local.MoviesEntity
import project.movies.searchformovies.domain.repositories.MoviesRepository
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailInformationViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    fun saveFavoritesMovie(favoritesMovie: MoviesEntity) {
        viewModelScope.launch {
            try {
                repository.saveFavoritesMovie(favoritesMovie)
            } catch (ex: IOException) {
                Timber.e(ex)
            }
        }
    }
}