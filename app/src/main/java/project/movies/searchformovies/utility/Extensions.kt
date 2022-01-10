package project.movies.searchformovies.utility

import android.content.Context
import android.util.DisplayMetrics

fun Int.convertPixelFromDp(context: Context): Int {
    val density = context.resources.displayMetrics.densityDpi
    val pixelInDp = density / DisplayMetrics.DENSITY_DEFAULT
    return pixelInDp * this
}

fun Int?.orZero() = this ?: 0