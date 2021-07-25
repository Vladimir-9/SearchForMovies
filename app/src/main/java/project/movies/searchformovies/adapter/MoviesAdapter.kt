package project.movies.searchformovies.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import project.movies.searchformovies.remote.RemoteMoviesData

class MoviesAdapter : AsyncListDifferDelegationAdapter<RemoteMoviesData>(MoviesDiffUtil()) {

    init {
        delegatesManager.addDelegate(MoviesAdapterDelegate())
    }

    class MoviesDiffUtil : DiffUtil.ItemCallback<RemoteMoviesData>() {
        override fun areItemsTheSame(
            oldItem: RemoteMoviesData,
            newItem: RemoteMoviesData
        ): Boolean {
            return oldItem.image == newItem.image
        }

        override fun areContentsTheSame(
            oldItem: RemoteMoviesData,
            newItem: RemoteMoviesData
        ): Boolean {
            return oldItem == newItem
        }

    }
}