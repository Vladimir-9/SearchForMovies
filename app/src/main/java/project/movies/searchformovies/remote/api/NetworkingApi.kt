package project.movies.searchformovies.remote.api

import project.movies.searchformovies.remote.RemoteMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkingApi {
    @GET("3/discover/movie")
    suspend fun popularMovies(
        @Query("language") language: String = "ru-RU",
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("query") query: String = "",
        @Query("page") page: Int = 1
    ): RemoteMovies

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("language") language: String = "ru-RU",
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): RemoteMovies
}