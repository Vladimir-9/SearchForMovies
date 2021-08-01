package project.movies.searchformovies.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import project.movies.searchformovies.networking.EverythingMoviesPagingSource
import project.movies.searchformovies.networking.Networking
import project.movies.searchformovies.remote.MoviesData
import project.movies.searchformovies.remote.RemoteMovies
import timber.log.Timber

class MoviesViewModel : ViewModel() {

    private val repository = MoviesRepository()
    private var popularMovies: RemoteMovies? = null
    private val _moviesStateFlow = repository.pagerMovies()
    val moviesStateFlow: Flow<PagingData<MoviesData>>
        get() = _moviesStateFlow
//
//    init {
//        getPopularMovies()
//    }

    private fun getPopularMovies(): Flow<PagingData<MoviesData>> {
        return repository.pagerMovies()

//        _moviesStateFlow.value = MoviesLoadState.LoadState
//        when (val resultMovies = repository.searchPopularMovies()) {
//            is MoviesLoadState.Success -> {
//                Timber.e("MoviesLoadState.Success")
//                // popularMovies = resultMovies.movies
//                _moviesStateFlow.value = resultMovies
//            }
//            is MoviesLoadState.Error -> {
//                Timber.e("MoviesLoadState.Error")
//                _moviesStateFlow.value = resultMovies
//            }
//        }

    }


    fun getSearchMovies(searchResponse: String) {
        when {
            searchResponse != "" -> searchMovies(searchResponse)
//            popularMovies != null -> _moviesStateFlow.value =
//                MoviesLoadState.Success(popularMovies!!)
            else -> getPopularMovies()
        }
    }

    private fun searchMovies(searchResponse: String) {
        viewModelScope.launch {
//            _moviesStateFlow.value = MoviesLoadState.LoadState
//            val awaitMovies = async { repository.searchMovies(Dispatchers.IO, searchResponse) }
//            when (val movies = awaitMovies.await()) {
//                is MoviesLoadState.Success -> {
//                    _moviesStateFlow.value = movies
//                }
//                is MoviesLoadState.Error -> {
//                    _moviesStateFlow.value = movies
//                }
//            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        popularMovies = null
    }
}

sealed class MoviesLoadState {
    data class Success(val movies: Flow<PagingData<MoviesData>>) : MoviesLoadState()
    data class Error(val errorMessage: String?) : MoviesLoadState()
    object LoadState : MoviesLoadState()
}
