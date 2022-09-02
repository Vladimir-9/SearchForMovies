package project.movies.searchformovies.data

import com.nhaarman.mockitokotlin2.any
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import project.movies.searchformovies.data.local.MoviesDao
import project.movies.searchformovies.data.remote.NetworkingApi
import project.movies.searchformovies.data.remote.dto.RemoteMoviesDto
import project.movies.searchformovies.domain.model.MoviesData
import project.movies.searchformovies.utility.Resource
import retrofit2.Response

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
    fun successPopularMoviesState() {
        mockSearchOrGetPopularMoviesSuccess(true)
        runBlocking {
            val actual = repository.searchPopularMovies().javaClass
            val expected = Resource.Success<List<MoviesData>>(emptyList()).javaClass
            assertEquals(expected, actual)
        }
    }

    @Test
    fun successPopularMoviesData() {
        mockSearchOrGetPopularMoviesSuccess(true)
        runBlocking {
            val actual = repository.searchPopularMovies().data
            val expected = Resource.Success<List<MoviesData>>(emptyList()).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorPopularMoviesState() {
        mockSearchOrGetPopularMoviesError(true)
        runBlocking {
            val actual = repository.searchPopularMovies().javaClass
            val expected = Resource.Error<List<MoviesData>>(1, "").javaClass
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorPopularMoviesData() {
        mockSearchOrGetPopularMoviesError(true)
        runBlocking {
            val actual = repository.searchPopularMovies().code
            val expected = Resource.Error<List<MoviesData>>(404, "").code
            assertEquals(expected, actual)
        }
    }

    @Test
    fun successSearchMoviesState() {
        mockSearchOrGetPopularMoviesSuccess(false)
        runBlocking {
            val actual = repository.searchMovies("text").javaClass
            val expected = Resource.Success<List<MoviesData>>(emptyList()).javaClass
            assertEquals(expected, actual)
        }
    }

    @Test
    fun successSearchMoviesData() {
        mockSearchOrGetPopularMoviesSuccess(false)
        runBlocking {
            val actual = repository.searchMovies("text").data
            val expected = Resource.Success<List<MoviesData>>(emptyList()).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorSearchMoviesState() {
        mockSearchOrGetPopularMoviesError(false)
        runBlocking {
            val actual = repository.searchMovies("text").javaClass
            val expected = Resource.Error<List<MoviesData>>(null, null).javaClass
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorSearchMoviesData() {
        mockSearchOrGetPopularMoviesError(false)
        runBlocking {
            val actual = repository.searchMovies("text").code
            val expected = Resource.Error<List<MoviesData>>(404, null).code
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

    private fun mockSearchOrGetPopularMoviesSuccess(isPopular: Boolean) {
        runBlocking {
            if (isPopular)
                Mockito.`when`(networkingApi.popularMovies(any(), any(), any(), any()))
                    .thenReturn(mockSuccessResponse)
            else
                Mockito.`when`(networkingApi.searchMovies(any(), any(), any()))
                    .thenReturn(mockSuccessResponse)
        }
    }

    private fun mockSearchOrGetPopularMoviesError(isPopular: Boolean) {
        runBlocking {
            if (isPopular)
                Mockito.`when`(networkingApi.popularMovies(any(), any(), any(), any()))
                    .thenReturn(mockErrorResponse)
            else
                Mockito.`when`(networkingApi.searchMovies(any(), any(), any()))
                    .thenReturn(mockErrorResponse)
        }
    }

    companion object {
        val mockSuccessResponse: Response<RemoteMoviesDto> =
            Response.success(RemoteMoviesDto(1, emptyList()))
        val mockErrorResponse: Response<RemoteMoviesDto> =
            Response.error(404, RealResponseBody("", 1L, Buffer()))
    }
}