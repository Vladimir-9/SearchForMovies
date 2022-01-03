package project.movies.searchformovies.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import project.movies.searchformovies.R
import project.movies.searchformovies.databinding.ItemMovieBinding
import project.movies.searchformovies.remote.MoviesData

class MoviesAdapterDelegate(
    private var width: Int,
    private val height: Int,
    private val itemClick: (movies: MoviesData) -> Unit
) : AbsListItemAdapterDelegate<MoviesData, MoviesData, MoviesAdapterDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        view.layoutParams = RecyclerView.LayoutParams(width, height)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(
        item: MoviesData,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    override fun isForViewType(
        item: MoviesData,
        items: MutableList<MoviesData>,
        position: Int
    ): Boolean {
        return true
    }

    class ViewHolder(itemView: View, private val itemClick: (movies: MoviesData) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var viewBinding: ItemMovieBinding

        fun bind(movies: MoviesData) {
            viewBinding = ItemMovieBinding.bind(itemView)
            viewBinding.ivMovie.clipToOutline = true

            Glide
                .with(itemView)
                .load(PATH_LOAD_IMAGE + movies.backdrop_path)
                .placeholder(R.drawable.ic_movie)
                .error(R.drawable.ic_not_poster)
                .into(viewBinding.ivMovie)
            viewBinding.twTitleMovie.text = movies.title
            viewBinding.twDescription.text = movies.description
            viewBinding.twReleaseDate.text = movies.release_date
            sendMovie(movies)
        }

        private fun sendMovie(movies: MoviesData) {
            itemView.setOnClickListener {
                itemClick(movies)
            }
        }
    }

    companion object {
        const val PATH_LOAD_IMAGE = "https://image.tmdb.org/t/p/w500"
    }
}