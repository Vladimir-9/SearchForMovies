package project.movies.searchformovies.data.mapper

import project.movies.searchformovies.data.remote.dto.DataDetailsDrinkDto
import project.movies.searchformovies.data.remote.dto.DetailsDrinkDto
import project.movies.searchformovies.domain.model.DataDetailsDrink
import project.movies.searchformovies.domain.model.DetailsDrink

fun DetailsDrinkDto?.toDetailsDrink() = DetailsDrink(
    drinks = this?.drinks?.map { it.toDataDetailsDrink() }.orEmpty()
)

fun DataDetailsDrinkDto?.toDataDetailsDrink() = DataDetailsDrink(
    idDrink = this?.idDrink.orEmpty(),
    strDrink = this?.strDrink.orEmpty(),
    strInstructions = this?.strInstructions.orEmpty(),
    strDrinkThumb = this?.strDrinkThumb.orEmpty(),
    strIngredient1 = this?.strIngredient1.orEmpty(),
    strIngredient2 = this?.strIngredient2.orEmpty(),
    strIngredient3 = this?.strIngredient3.orEmpty(),
    strIngredient4 = this?.strIngredient4.orEmpty(),
    strIngredient5 = this?.strIngredient5.orEmpty(),
    strIngredient6 = this?.strIngredient6.orEmpty(),
    strIngredient7 = this?.strIngredient7.orEmpty(),
    strIngredient8 = this?.strIngredient8.orEmpty(),
    strIngredient9 = this?.strIngredient9.orEmpty(),
    strIngredient10 = this?.strIngredient10.orEmpty(),
)