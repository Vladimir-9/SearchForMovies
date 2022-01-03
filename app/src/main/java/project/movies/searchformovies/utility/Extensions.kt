package project.movies.searchformovies.utility

import android.content.Context
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Int.convertPixelFromDp(context: Context): Int {
    val density = context.resources.displayMetrics.densityDpi
    val pixelInDp = density / DisplayMetrics.DENSITY_DEFAULT
    return pixelInDp * this
}

fun Fragment.toast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Int?.orZero() = this ?: 0