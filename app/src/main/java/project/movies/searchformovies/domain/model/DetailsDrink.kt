package project.movies.searchformovies.domain.model

data class DetailsDrink(
    val drinks: List<DataDetailsDrink>
)

data class DataDetailsDrink(
    val idDrink: String,
    val strDrink: String,
    val strInstructions: String,
    val strDrinkThumb: String,
    val strIngredient1: String,
    val strIngredient2: String,
    val strIngredient3: String,
    val strIngredient4: String,
    val strIngredient5: String,
    val strIngredient6: String,
    val strIngredient7: String,
    val strIngredient8: String,
    val strIngredient9: String,
    val strIngredient10: String,
) {
    val allIngredientsList = listOf(
        strIngredient1,
        strIngredient2,
        strIngredient3,
        strIngredient4,
        strIngredient5,
        strIngredient6,
        strIngredient7,
        strIngredient8,
        strIngredient9,
        strIngredient10
    )
}