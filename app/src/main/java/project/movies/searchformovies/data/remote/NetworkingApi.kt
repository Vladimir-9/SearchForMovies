package project.movies.searchformovies.data.remote

import project.movies.searchformovies.data.remote.dto.DetailsDrinkDto
import project.movies.searchformovies.data.remote.dto.DrinksDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkingApi {
    @GET("/api/json/v1/1/filter.php")
    suspend fun alcoholicDrinks(
        @Query("a") filter: String = "Alcoholic"
    ): Response<DrinksDto>

    @GET("/api/json/v1/1/lookup.php")
    suspend fun detailDesc(
        @Query("i") id: String,
    ): Response<DetailsDrinkDto>
}