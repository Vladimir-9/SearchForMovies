package project.movies.searchformovies.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import project.movies.searchformovies.remote.MoviesData

class MoviesAdapter(itemClick: (movies: MoviesData) -> Unit) :
    AsyncListDifferDelegationAdapter<MoviesData>(MoviesDiffUtil()) {

    init {
        delegatesManager.addDelegate(MoviesAdapterDelegate(itemClick))
    }

    class MoviesDiffUtil : DiffUtil.ItemCallback<MoviesData>() {
        override fun areItemsTheSame(oldItem: MoviesData, newItem: MoviesData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MoviesData, newItem: MoviesData): Boolean {
            return oldItem.id == newItem.id
        }

    }
}