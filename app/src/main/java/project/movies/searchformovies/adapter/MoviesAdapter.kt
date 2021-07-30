package project.movies.searchformovies.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import project.movies.searchformovies.remote.MoviesData

class MoviesAdapter : AsyncListDifferDelegationAdapter<MoviesData>(MoviesDiffUtil()) {

    init {
        delegatesManager.addDelegate(MoviesAdapterDelegate())
    }

    class MoviesDiffUtil : DiffUtil.ItemCallback<MoviesData>() {
        override fun areItemsTheSame(
            oldItem: MoviesData,
            newItem: MoviesData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MoviesData,
            newItem: MoviesData
        ): Boolean {
            return oldItem == newItem
        }

    }
}