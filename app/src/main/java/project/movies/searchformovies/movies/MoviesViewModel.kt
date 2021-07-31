package project.movies.searchformovies.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import project.movies.searchformovies.remote.MoviesData

class MoviesViewModel : ViewModel() {

    private val repository = MoviesRepository()
    private var popularMovies: List<MoviesData>? = null
    private val _moviesStateFlow: MutableStateFlow<MoviesLoadState> =
        MutableStateFlow(MoviesLoadState.Success(listOf()))
    val moviesStateFlow: StateFlow<MoviesLoadState>
        get() = _moviesStateFlow

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _moviesStateFlow.value = MoviesLoadState.LoadState
            val awaitPopularMovies = async {
                repository.searchPopularMovies(Dispatchers.IO)
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
            popularMovies != null -> _moviesStateFlow.value =
                MoviesLoadState.Success(popularMovies!!)
            else -> getPopularMovies()
        }
    }

    private fun searchMovies(searchResponse: String) {
        viewModelScope.launch {
            _moviesStateFlow.value = MoviesLoadState.LoadState
            val awaitMovies = async { repository.searchMovies(Dispatchers.IO, searchResponse) }
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

    override fun onCleared() {
        super.onCleared()
        popularMovies = null
    }
}

sealed class MoviesLoadState {
    data class Success(val listMovies: List<MoviesData>) : MoviesLoadState()
    data class Error(val errorMessage: String?) : MoviesLoadState()
    object LoadState : MoviesLoadState()
}
