package project.movies.searchformovies.presentation.movies_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import project.movies.searchformovies.domain.model.DrinksData
import project.movies.searchformovies.domain.model.DrinksViewState
import project.movies.searchformovies.domain.repositories.DrinksRepository
import project.movies.searchformovies.utility.Resource
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: DrinksRepository) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.postValue(_viewState.value?.copy(
                error = throwable.message.orEmpty(),
                isLoading = false
            ))
    }

    private val _viewState = MutableLiveData(DrinksViewState())
    val viewState: LiveData<DrinksViewState> = _viewState

    init {
        getAlcoholicDrinks()
    }

    private fun getAlcoholicDrinks() {
        viewModelScope.launch(exceptionHandler) {
            _viewState.postValue(_viewState.value?.copy(isLoading = true))
            val resListDrinks = repository.alcoholicDrinks()
            setResult(resListDrinks)
        }
    }

//    private fun searchMovies(searchResponse: String) {
//        viewModelScope.launch(exceptionHandler) {
//            _viewState.postValue(_viewState.value?.copy(isLoading = true))
//            val resListMovies = repository.searchMovies(searchResponse)
//            setResult(resListMovies)
//        }
//    }

    private fun setResult(resListMovies: Resource<List<DrinksData>>){
        when (resListMovies) {
            is Resource.Success -> _viewState.postValue(_viewState.value?.copy(
                listDrinks = resListMovies.data.orEmpty(),
                isLoading = false,
                error = ""
            ))
            is Resource.Error -> _viewState.postValue(_viewState.value?.copy(
                error = "error",
                isLoading = false
            ))
        }
    }

//    fun getSearchMovies(searchResponse: String) {
//        if (searchResponse.isNotBlank()) searchMovies(searchResponse)
//        else getAlcoholicDrinks()
//    }
}