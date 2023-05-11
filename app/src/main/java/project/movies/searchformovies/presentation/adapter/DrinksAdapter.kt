package project.movies.searchformovies.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import project.movies.searchformovies.domain.model.DrinksData

class DrinksAdapter(itemClick: (movies: DrinksData) -> Unit) :
    AsyncListDifferDelegationAdapter<DrinksData>(MoviesDiffUtil()) {

    init {
        delegatesManager.addDelegate(DrinksAdapterDelegate(itemClick))
    }

    class MoviesDiffUtil : DiffUtil.ItemCallback<DrinksData>() {
        override fun areItemsTheSame(oldItem: DrinksData, newItem: DrinksData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DrinksData, newItem: DrinksData): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

    }
}