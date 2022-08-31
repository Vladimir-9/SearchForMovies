package project.movies.searchformovies.utility

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import retrofit2.Response

fun Int.convertPixelFromDp(context: Context): Int {
    val density = context.resources.displayMetrics.densityDpi
    val pixelInDp = density / DisplayMetrics.DENSITY_DEFAULT
    return pixelInDp * this
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Int?.orZero(): Int = this ?: 0

fun <T, S> Response<T>.toResource(
    transform: (source: T?) -> S
): Resource<S> {
    return if (isSuccessful)
        Resource.Success(
            transform(body())
        )
    else Resource.Error(code(), errorBody()?.string())
}

fun View?.hideKeyboard() {
    this?.let {
        val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}