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
import project.movies.searchformovies.data.remote.dto.DrinksDto
import project.movies.searchformovies.domain.model.DrinksData
import project.movies.searchformovies.utility.Resource
import retrofit2.Response

class DrinksRepositoryImplTest {

    private lateinit var repository: DrinksRepositoryImpl
    private lateinit var favoritesMovieDao: MoviesDao
    private lateinit var networkingApi: NetworkingApi

    @Before
    fun init() {
        favoritesMovieDao = Mockito.mock(MoviesDao::class.java)
        networkingApi = Mockito.mock(NetworkingApi::class.java)
        repository = DrinksRepositoryImpl(favoritesMovieDao, networkingApi)
    }

    @Test
    fun successPopularMoviesState() {
        mockSearchOrGetPopularMoviesSuccess(true)
        runBlocking {
            val actual = repository.alcoholicDrinks().javaClass
            val expected = Resource.Success<List<DrinksData>>(emptyList()).javaClass
            assertEquals(expected, actual)
        }
    }

    @Test
    fun successPopularMoviesData() {
        mockSearchOrGetPopularMoviesSuccess(true)
        runBlocking {
            val actual = repository.alcoholicDrinks().data
            val expected = Resource.Success<List<DrinksData>>(emptyList()).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorPopularMoviesState() {
        mockSearchOrGetPopularMoviesError(true)
        runBlocking {
            val actual = repository.alcoholicDrinks().javaClass
            val expected = Resource.Error<List<DrinksData>>(1, "").javaClass
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorPopularMoviesData() {
        mockSearchOrGetPopularMoviesError(true)
        runBlocking {
            val actual = repository.alcoholicDrinks().code
            val expected = Resource.Error<List<DrinksData>>(404, "").code
            assertEquals(expected, actual)
        }
    }

    @Test
    fun successSearchMoviesState() {
        mockSearchOrGetPopularMoviesSuccess(false)
        runBlocking {
            val actual = repository.searchDrinks("text").javaClass
            val expected = Resource.Success<List<DrinksData>>(emptyList()).javaClass
            assertEquals(expected, actual)
        }
    }

    @Test
    fun successSearchMoviesData() {
        mockSearchOrGetPopularMoviesSuccess(false)
        runBlocking {
            val actual = repository.searchDrinks("text").data
            val expected = Resource.Success<List<DrinksData>>(emptyList()).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorSearchMoviesState() {
        mockSearchOrGetPopularMoviesError(false)
        runBlocking {
            val actual = repository.searchDrinks("text").javaClass
            val expected = Resource.Error<List<DrinksData>>(null, null).javaClass
            assertEquals(expected, actual)
        }
    }

    @Test
    fun errorSearchMoviesData() {
        mockSearchOrGetPopularMoviesError(false)
        runBlocking {
            val actual = repository.searchDrinks("text").code
            val expected = Resource.Error<List<DrinksData>>(404, null).code
            assertEquals(expected, actual)
        }
    }

    @Test
    fun getAllFavoritesMovie() {
        runBlocking {
            Mockito.`when`(favoritesMovieDao.getAllMovies()).thenReturn(emptyList())
            val actual = repository.getAllFavoritesMovie()
            val expected = emptyList<DrinksData>()
            assertEquals(expected, actual)
        }
    }

    private fun mockSearchOrGetPopularMoviesSuccess(isPopular: Boolean) {
        runBlocking {
            if (isPopular)
                Mockito.`when`(networkingApi.alcoholicDrinks(any()))
                    .thenReturn(mockSuccessResponse)
            else
                Mockito.`when`(networkingApi.searchDrinks(any()))
                    .thenReturn(mockSuccessResponse)
        }
    }

    private fun mockSearchOrGetPopularMoviesError(isPopular: Boolean) {
        runBlocking {
            if (isPopular)
                Mockito.`when`(networkingApi.alcoholicDrinks(any()))
                    .thenReturn(mockErrorResponse)
            else
                Mockito.`when`(networkingApi.searchDrinks(any()))
                    .thenReturn(mockErrorResponse)
        }
    }

    companion object {
        val mockSuccessResponse: Response<DrinksDto> =
            Response.success(DrinksDto(emptyList()))
        val mockErrorResponse: Response<DrinksDto> =
            Response.error(404, RealResponseBody("", 1L, Buffer()))
    }
}