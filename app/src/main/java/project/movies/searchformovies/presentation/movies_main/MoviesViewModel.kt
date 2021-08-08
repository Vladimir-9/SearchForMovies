package project.movies.searchformovies.presentation.movies_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import project.movies.searchformovies.data.MoviesRepository
import project.movies.searchformovies.remote.MoviesData
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private lateinit var popularMovies: List<MoviesData>
    private val _moviesStateFlow: MutableStateFlow<MoviesLoadState> =
        MutableStateFlow(MoviesLoadState.Success(listOf()))
    val moviesStateFlow: StateFlow<MoviesLoadState> = _moviesStateFlow.asStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _moviesStateFlow.value = MoviesLoadState.LoadState
            val awaitPopularMovies = async {
                repository.searchPopularMovies()
            }
            when (val movies = awaitPopularMovies.await()) {
                is MoviesLoadState.Success -> {
                    popularMovies = movies.listMovies
                    _moviesStateFlow.value = movies
                }
                is MoviesLoadState.Error -> {
                    _moviesStateFlow.value = movies
                }
            }
        }
    }

    fun getSearchMovies(searchResponse: String) {
        when {
            searchResponse != "" -> searchMovies(searchResponse)
            popularMovies.isEmpty().not() -> _moviesStateFlow.value =
                MoviesLoadState.Success(popularMovies)
            else -> getPopularMovies()
        }
    }

    private fun searchMovies(searchResponse: String) {
        viewModelScope.launch {
            _moviesStateFlow.value = MoviesLoadState.LoadState
            val awaitMovies = async { repository.searchMovies(searchResponse) }
            when (val movies = awaitMovies.await()) {
                is MoviesLoadState.Success -> {
                    _moviesStateFlow.value = movies
                }
                is MoviesLoadState.Error -> {
                    _moviesStateFlow.value = movies
                }
            }
        }
    }
}

sealed class MoviesLoadState {
    data class Success(val listMovies: List<MoviesData>) : MoviesLoadState()
    data class Error(val errorMessage: String?) : MoviesLoadState()
    object LoadState : MoviesLoadState()
}
