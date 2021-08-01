package project.movies.searchformovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import project.movies.searchformovies.R
import project.movies.searchformovies.databinding.ItemMovieBinding
import project.movies.searchformovies.remote.MoviesData

class MoviesAdapterDelegate :
    PagingDataAdapter<MoviesData, MoviesAdapterDelegate.ViewHolder>(MoviesDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var viewBinding: ItemMovieBinding

        fun bind(movies: MoviesData) {
            viewBinding = ItemMovieBinding.bind(itemView)
            viewBinding.ivMovie.clipToOutline = true

            Glide
                .with(itemView)
                .load(PATH_LOAD_IMAGE + movies.poster_path)
                .placeholder(R.drawable.ic_movie)
                .error(R.drawable.ic_not_poster)
                .into(viewBinding.ivMovie)
            viewBinding.twTitleMovie.text = movies.title
            viewBinding.twDescription.text = movies.description
            viewBinding.twReleaseDate.text = movies.release_date
        }
    }

    class MoviesDiffUtil : DiffUtil.ItemCallback<MoviesData>() {
        override fun areItemsTheSame(oldItem: MoviesData, newItem: MoviesData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MoviesData, newItem: MoviesData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    companion object {
        private const val PATH_LOAD_IMAGE = "https://image.tmdb.org/t/p/w500"
    }
}