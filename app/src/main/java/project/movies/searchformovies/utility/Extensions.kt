package project.movies.searchformovies.utility

import android.content.Context
import android.content.DialogInterface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

fun Context.getColorCompat(@ColorRes color: Int) =
    ResourcesCompat.getColor(this.resources, color, null)

fun Context.getDrawableCompat(@DrawableRes draw: Int) = AppCompatResources.getDrawable(this, draw)

fun Context.getAlertDialog(
    title: CharSequence,
    @StringRes textPositive: Int,
    @StringRes textNegative: Int,
    @DrawableRes background: Int,
    positiveListener: DialogInterface.OnClickListener
): AlertDialog = MaterialAlertDialogBuilder(this).apply {
    setTitle(title)
    setBackground(this@getAlertDialog.getDrawableCompat(background))
    setPositiveButton(getString(textPositive), positiveListener)
    setNegativeButton(getString(textNegative)) { dialog, _ ->
        dialog.cancel()
    }
}.show()

fun Context.getTextWithColor(text: SpannableString, @ColorRes textColor: Int): SpannableString {
    text.setSpan(
        ForegroundColorSpan(this.getColorCompat(textColor)),
        0,
        text.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return text
}