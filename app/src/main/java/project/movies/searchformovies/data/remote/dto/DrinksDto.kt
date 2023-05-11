package project.movies.searchformovies.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DrinksDto(
    @Json(name = "drinks")
    val drinks: List<DrinkDataDto>?
)

@JsonClass(generateAdapter = true)
data class DrinkDataDto(
    @Json(name = "idDrink")
    val idDrink: String?,
    @Json(name = "strDrink")
    val strDrink: String?,
    @Json(name = "strDrinkThumb")
    val strDrinkThumb: String?
)