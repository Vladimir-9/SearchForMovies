package project.movies.searchformovies.data.remote

import project.movies.searchformovies.data.remote.dto.RemoteMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkingApi {
    @GET("3/discover/movie")
    suspend fun popularMovies(
        @Query("language") language: String = "ru-RU",
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("query") query: String = "",
        @Query("page") page: Int = 1
    ): RemoteMoviesDto

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("language") language: String = "ru-RU",
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): RemoteMoviesDto
}