package project.movies.searchformovies.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import project.movies.searchformovies.R
import project.movies.searchformovies.databinding.ItemMovieBinding
import project.movies.searchformovies.domain.model.DrinksData

class DrinksAdapterDelegate(private val itemClick: (drinks: DrinksData) -> Unit) :
    AbsListItemAdapterDelegate<DrinksData, DrinksData, DrinksAdapterDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_movie, parent, false), itemClick)
    }

    override fun onBindViewHolder(
        item: DrinksData,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    override fun isForViewType(
        item: DrinksData,
        items: MutableList<DrinksData>,
        position: Int
    ): Boolean {
        return true
    }

    class ViewHolder(itemView: View, private val itemClick: (drinks: DrinksData) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var viewBinding: ItemMovieBinding

        fun bind(drinks: DrinksData) {
            viewBinding = ItemMovieBinding.bind(itemView)
            with(viewBinding) {
                ivMovie.clipToOutline = true
                twTitleMovie.text = drinks.strDrink
                twDescription.text = drinks.strDrink
            }

            Glide
                .with(itemView)
                .load(drinks.strDrinkThumb)
                .placeholder(R.drawable.ic_movie)
                .error(R.drawable.ic_not_poster)
                .into(viewBinding.ivMovie)

            sendMovie(drinks)
        }

        private fun sendMovie(movies: DrinksData) {
            itemView.setOnClickListener {
                itemClick(movies)
            }
        }
    }
}