package project.movies.searchformovies.viewpager

import android.view.View
import project.movies.searchformovies.viewpager.ViewPagerLayoutManager.ItemTransformer
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class CurveTransformer : ItemTransformer {

    override fun transformItem(layoutManager: ViewPagerLayoutManager?, item: View?, fraction: Float) {
        if (layoutManager?.orientation == ViewPagerLayoutManager.VERTICAL && item == null) {
            return
        }
        with(item!!) {
            val width = width
            val height = height
            pivotX = width / 2f
            pivotY = height.toFloat()
            val scale = 1 - 0.1f * abs(fraction)
            scaleX = scale
            scaleY = scale
            rotation = ROTATE_ANGEL * fraction
            translationY =
                (sin(2 * Math.PI * ROTATE_ANGEL * abs(fraction) / 360f) * width / 2.0f).toFloat()
            translationX =
                ((1 - scale) * width / 2.0f / cos(2 * Math.PI * ROTATE_ANGEL * fraction / 360f)).toFloat()
            if (fraction > 0) {
                translationX = -translationX
            }
            alpha = 1 - 0.2f * abs(fraction)
        }
    }

    companion object {
        private const val ROTATE_ANGEL = 7
    }
}