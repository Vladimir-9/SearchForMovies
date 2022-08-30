package project.movies.searchformovies.utility

import android.content.Context
import android.util.DisplayMetrics
import android.widget.Toast

fun Int.convertPixelFromDp(context: Context): Int {
    val density = context.resources.displayMetrics.densityDpi
    val pixelInDp = density / DisplayMetrics.DENSITY_DEFAULT
    return pixelInDp * this
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Int?.orZero(): Int = this ?: 0