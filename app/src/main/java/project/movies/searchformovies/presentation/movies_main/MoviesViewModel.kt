package project.movies.searchformovies.presentation.movies_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import project.movies.searchformovies.data.MoviesRepository
import project.movies.searchformovies.remote.MoviesData
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private var popularMovies = listOf<MoviesData>()
    private val _moviesStateFlow = MutableLiveData<MoviesLoadState>()
    val moviesStateFlow: LiveData<MoviesLoadState>
        get() = _moviesStateFlow

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _moviesStateFlow.value = MoviesLoadState.LoadState
            val awaitPopularMoviesState = async {
                repository.searchPopularMovies()
            }
            val moviesState = awaitPopularMoviesState.await()
            getStatePopularMovies(moviesState)
        }
    }

    private fun getStatePopularMovies(moviesState: MoviesLoadState) {
        when (moviesState) {
            is MoviesLoadState.Success -> {
                popularMovies = moviesState.listMovies
                _moviesStateFlow.value = MoviesLoadState.Success(moviesState.listMovies)
            }
            is MoviesLoadState.Error -> {
                _moviesStateFlow.value = MoviesLoadState.Error("getStatePopularMovies")
            }
        }
    }

    fun getSearchMovies(searchResponse: String) {
        when {
            searchResponse != "" -> searchMovies(searchResponse)
            popularMovies.isNotEmpty() -> _moviesStateFlow.value =
                MoviesLoadState.Success(popularMovies)
            else -> getPopularMovies()
        }
    }

    private fun searchMovies(searchResponse: String) {
        viewModelScope.launch {
            _moviesStateFlow.value = MoviesLoadState.LoadState
            val awaitMoviesState = async { repository.searchMovies(searchResponse) }
            val moviesLoadState = awaitMoviesState.await()
            getStateSearchMovies(moviesLoadState)
        }
    }

    private fun getStateSearchMovies(moviesState: MoviesLoadState) {
        when (moviesState) {
            is MoviesLoadState.Success ->
                _moviesStateFlow.value = MoviesLoadState.Success(moviesState.listMovies)
            is MoviesLoadState.Error ->
                _moviesStateFlow.value = MoviesLoadState.Error("getStateSearchMovies")
        }
    }
}

sealed class MoviesLoadState {
    data class Success(val listMovies: List<MoviesData>) : MoviesLoadState()
    data class Error(val errorMessage: String?) : MoviesLoadState()
    object LoadState : MoviesLoadState()
}
