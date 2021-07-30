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
    private val _moviesStateFlow = MutableStateFlow(MoviesLoadState.Success(listOf()))
    val moviesStateFlow: StateFlow<MoviesLoadState>
        get() = _moviesStateFlow

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            val awaitPopularMovies = async {
                repository.searchPopularMovies(Dispatchers.IO)
            }
            when (val movies = awaitPopularMovies.await()) {
                is MoviesLoadState.Success -> {
                    popularMovies = movies.listMovies
                    _moviesStateFlow.value = movies
                }
                is MoviesLoadState.Error -> {
                }
            }
        }
    }

    fun getSearchMovies(searchResponse: String, isPopular: Boolean) {
        viewModelScope.launch {
            if (isPopular.not()) {
                val awaitMovies = async { repository.searchMovies(Dispatchers.IO, searchResponse) }
                when (val movies = awaitMovies.await()) {
                    is MoviesLoadState.Success -> {
                        _moviesStateFlow.value = movies
                    }
                    is MoviesLoadState.Error -> {
                    }
                }
            } else {
                _moviesStateFlow.value = MoviesLoadState.Success(popularMovies ?: emptyList())
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
