package project.movies.searchformovies.data.mapper

import project.movies.searchformovies.data.local.DrinksEntity
import project.movies.searchformovies.data.remote.dto.DrinkDataDto
import project.movies.searchformovies.data.remote.dto.DrinksDto
import project.movies.searchformovies.domain.model.DrinksData
import project.movies.searchformovies.domain.model.Drinks

fun DrinksDto.toDrinks() = Drinks(
    drinks = this.drinks?.map { it.toDrinksData() }.orEmpty()
)

fun DrinkDataDto?.toDrinksData() = DrinksData(
    idDrink = this?.idDrink.orEmpty(),
    strDrink = this?.strDrink.orEmpty(),
    strDrinkThumb = this?.strDrinkThumb.orEmpty()
)

fun DrinksEntity.toMoviesData() = DrinksData(
    idDrink = this.id.toString(),
    strDrink = this.name,
    strDrinkThumb = this.image
)

fun DrinksData.toMoviesEntity() = DrinksEntity(
    id = this.idDrink.toInt(),
    name = this.strDrink,
    image = this.strDrinkThumb
)
