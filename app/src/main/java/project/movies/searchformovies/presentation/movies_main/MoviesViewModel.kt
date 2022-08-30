package project.movies.searchformovies.presentation.movies_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import project.movies.searchformovies.domain.model.MoviesData
import project.movies.searchformovies.domain.repositories.MoviesRepository
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private var popularMovies = listOf<MoviesData>()
    private val _moviesLiveDate = MutableLiveData<MoviesLoadState>()
    val moviesLiveDate: LiveData<MoviesLoadState>
        get() = _moviesLiveDate

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _moviesLiveDate.value = MoviesLoadState.LoadState
            val awaitPopularMoviesState = async {
                repository.searchPopularMovies()
            }
            val moviesList = awaitPopularMoviesState.await()
            getStatePopularMovies(moviesList)
        }
    }

    private fun getStatePopularMovies(moviesList: List<MoviesData>) {
        when {
             moviesList.isNotEmpty() -> {
                popularMovies = moviesList
                _moviesLiveDate.value = MoviesLoadState.Success(moviesList)
            }
             else -> {
                _moviesLiveDate.value = MoviesLoadState.Error("getStatePopularMovies")
            }
        }
    }

    fun getSearchMovies(searchResponse: String) {
        when {
            searchResponse != "" -> searchMovies(searchResponse)
            popularMovies.isNotEmpty() -> _moviesLiveDate.value =
                MoviesLoadState.Success(popularMovies)
            else -> getPopularMovies()
        }
    }

    private fun searchMovies(searchResponse: String) {
        viewModelScope.launch {
            _moviesLiveDate.value = MoviesLoadState.LoadState
            val awaitMoviesState = async { repository.searchMovies(searchResponse) }
            val moviesList = awaitMoviesState.await()
            getStateSearchMovies(moviesList)
        }
    }

    private fun getStateSearchMovies(moviesList: List<MoviesData>) {
        when {
            moviesList.isNotEmpty() ->
                _moviesLiveDate.value = MoviesLoadState.Success(moviesList)
            else ->
                _moviesLiveDate.value = MoviesLoadState.Error("getStateSearchMovies")
        }
    }
}

sealed class MoviesLoadState {
    data class Success(val listMovies: List<MoviesData>) : MoviesLoadState()
    data class Error(val errorMessage: String?) : MoviesLoadState()
    object LoadState : MoviesLoadState()
}
