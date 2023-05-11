package project.movies.searchformovies.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import project.movies.searchformovies.data.remote.dto.DrinkDataDto

data class Drinks(
    val drinks: List<DrinksData>
)

@Parcelize
data class DrinksData(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String
) : Parcelable