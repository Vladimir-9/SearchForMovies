package project.movies.searchformovies.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import project.movies.searchformovies.remote.MoviesData

class MoviesAdapter(width: Int = 0, height: Int = 0, itemClick: (movies: MoviesData) -> Unit) :
    AsyncListDifferDelegationAdapter<MoviesData>(MoviesDiffUtil()) {

    init {
        delegatesManager.addDelegate(MoviesAdapterDelegate(width, height, itemClick))
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