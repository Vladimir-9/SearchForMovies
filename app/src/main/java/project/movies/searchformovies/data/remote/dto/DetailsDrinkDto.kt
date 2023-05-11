package project.movies.searchformovies.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailsDrinkDto(
    @Json(name = "drinks")
    val drinks: List<DataDetailsDrinkDto>?
)

@JsonClass(generateAdapter = true)
data class DataDetailsDrinkDto(
    @Json(name = "idDrink")
    val idDrink: String?,
    @Json(name = "strDrink")
    val strDrink: String?,
    @Json(name = "strInstructions")
    val strInstructions: String?,
    @Json(name = "strDrinkThumb")
    val strDrinkThumb: String?,
    @Json(name = "strIngredient1")
    val strIngredient1: String?,
    @Json(name = "strIngredient2")
    val strIngredient2: String?,
    @Json(name = "strIngredient3")
    val strIngredient3: String?,
    @Json(name = "strIngredient4")
    val strIngredient4: String?,
    @Json(name = "strIngredient5")
    val strIngredient5: String?,
    @Json(name = "strIngredient6")
    val strIngredient6: String?,
    @Json(name = "strIngredient7")
    val strIngredient7: String?,
    @Json(name = "strIngredient8")
    val strIngredient8: String?,
    @Json(name = "strIngredient9")
    val strIngredient9: String?,
    @Json(name = "strIngredient10")
    val strIngredient10: String?,
)