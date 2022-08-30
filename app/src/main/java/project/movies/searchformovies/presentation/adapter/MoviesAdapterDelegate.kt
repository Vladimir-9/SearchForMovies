package project.movies.searchformovies.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import project.movies.searchformovies.R
import project.movies.searchformovies.databinding.ItemMovieBinding
import project.movies.searchformovies.domain.model.MoviesData

class MoviesAdapterDelegate(private val itemClick: (movies: MoviesData) -> Unit) :
    AbsListItemAdapterDelegate<MoviesData, MoviesData, MoviesAdapterDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_movie, parent, false), itemClick)
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
                .load(PATH_LOAD_IMAGE + movies.posterPath)
                .placeholder(R.drawable.ic_movie)
                .error(R.drawable.ic_not_poster)
                .into(viewBinding.ivMovie)
            viewBinding.twTitleMovie.text = movies.title
            viewBinding.twDescription.text = movies.description
            viewBinding.twReleaseDate.text = movies.releaseDate
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