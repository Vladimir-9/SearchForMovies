package project.movies.searchformovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import project.movies.searchformovies.R
import project.movies.searchformovies.databinding.ItemMovieBinding
import project.movies.searchformovies.remote.RemoteMoviesData

class MoviesAdapterDelegate :
    AbsListItemAdapterDelegate<RemoteMoviesData, RemoteMoviesData, MoviesAdapterDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(
        item: RemoteMoviesData,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    override fun isForViewType(
        item: RemoteMoviesData,
        items: MutableList<RemoteMoviesData>,
        position: Int
    ): Boolean {
        return true
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var viewBinding: ItemMovieBinding

        fun bind(movies: RemoteMoviesData) {
            viewBinding = ItemMovieBinding.bind(itemView)
            Glide
                .with(itemView)
                .load("https://images2.alphacoders.com/906/thumb-1920-906919.jpg")
                .placeholder(R.drawable.ic_movie)
                .into(viewBinding.ivMovie)
            viewBinding.twTitleMovie.text = movies.title
        }
    }
}