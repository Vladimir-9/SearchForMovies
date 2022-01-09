package project.movies.searchformovies.data

import com.nhaarman.mockitokotlin2.any
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import project.movies.searchformovies.data.db.MoviesDao
import project.movies.searchformovies.screens.viewmodel.MoviesLoadState
import project.movies.searchformovies.remote.MoviesData
import project.movies.searchformovies.remote.RemoteMovies
import project.movies.searchformovies.remote.api.NetworkingApi

class MoviesRepositoryImplTest {

    private lateinit var repository: MoviesRepositoryImpl
    private lateinit var favoritesMovieDao: MoviesDao
    private lateinit var networkingApi: NetworkingApi

    @Before
    fun init() {
        favoritesMovieDao = Mockito.mock(MoviesDao::class.java)
        networkingApi = Mockito.mock(NetworkingApi::class.java)
        repository = MoviesRepositoryImpl(favoritesMovieDao, networkingApi)
    }

    @Test
    fun successSearchPopularMovies() {
        mockSearchOrPopularMovies(true)
        runBlocking {
            val actual = repository.searchPopularMovies()
            val expected = MoviesLoadState.Success(emptyList())
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorSearchPopularMovies() {
        runBlocking {
            val actual = repository.searchPopularMovies()
            val expected = MoviesLoadState.Error("error")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun successSearchMovies() {
        mockSearchOrPopularMovies(false)
        runBlocking {
            val actual = repository.searchMovies("text")
            val expected = MoviesLoadState.Success(emptyList())
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorSearchMovies() {
        runBlocking {
            val actual = repository.searchMovies("text")
            val expected = MoviesLoadState.Error("error")
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getAllFavoritesMovie() {
        runBlocking {
            Mockito.`when`(favoritesMovieDao.getAllMovies()).thenReturn(emptyList())
            val actual = repository.getAllFavoritesMovie()
            val expected = emptyList<MoviesData>()
            assertEquals(expected, actual)
        }
    }

    private fun mockSearchOrPopularMovies(isPopular: Boolean) {
        runBlocking {
            if (isPopular) {
                Mockito.`when`(networkingApi.popularMovies(any(), any(), any(), any(), any()))
                    .thenReturn(mockRemoteMovies)
                    .thenThrow(RuntimeException())
            } else {
                Mockito.`when`(networkingApi.searchMovies(any(), any(), any(), any()))
                    .thenReturn(mockRemoteMovies)
                    .thenThrow(RuntimeException())
            }
        }
    }

    companion object {
        val mockRemoteMovies = RemoteMovies(1, emptyList())
    }
}