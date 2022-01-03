package project.movies.searchformovies.viewpager

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class ViewPagerRecycleView : RecyclerView {
    private var isFlingAble = true

    fun setFlingAble(flingAble: Boolean) { isFlingAble = flingAble }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        return if (isFlingAble) super.fling(velocityX, velocityY) else false
    }
}