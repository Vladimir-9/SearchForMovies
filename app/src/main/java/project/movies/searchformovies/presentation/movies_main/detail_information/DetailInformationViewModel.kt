package project.movies.searchformovies.presentation.movies_main.detail_information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import project.movies.searchformovies.data.local.DrinksEntity
import project.movies.searchformovies.domain.model.DataDetailsDrink
import project.movies.searchformovies.domain.repositories.DrinksRepository
import project.movies.searchformovies.utility.Resource
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailInformationViewModel @Inject constructor(
    private val repository: DrinksRepository
) : ViewModel() {

    private val _detailDesc = MutableLiveData<Resource<List<DataDetailsDrink>>>()
    val detailDesc: LiveData<Resource<List<DataDetailsDrink>>> = _detailDesc

    fun saveFavoritesMovie(favoritesMovie: DrinksEntity) {
        viewModelScope.launch {
            try {
                repository.saveFavoritesMovie(favoritesMovie)
            } catch (ex: IOException) {
                Timber.e(ex)
            }
        }
    }

    fun detailDesc(id: String) {
        viewModelScope.launch {
            try {
                val detailDesk = repository.detailDesc(id)
                _detailDesc.postValue(detailDesk)
            } catch (ex: IOException) {
                Timber.e(ex)
            }
        }
    }
}