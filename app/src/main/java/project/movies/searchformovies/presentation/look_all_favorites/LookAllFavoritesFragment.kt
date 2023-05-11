package project.movies.searchformovies.presentation.look_all_favorites

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import project.movies.searchformovies.R
import project.movies.searchformovies.databinding.FragmentLookAllFavoritesBinding
import project.movies.searchformovies.domain.model.DrinksData
import project.movies.searchformovies.presentation.adapter.DrinksAdapter
import project.movies.searchformovies.utility.MoviesItemDecoration
import project.movies.searchformovies.utility.autoCleared
import project.movies.searchformovies.utility.getAlertDialog
import project.movies.searchformovies.utility.getTextWithColor

@AndroidEntryPoint
class LookAllFavoritesFragment : Fragment(R.layout.fragment_look_all_favorites) {

    private var viewBinding: FragmentLookAllFavoritesBinding by autoCleared()
    private var adapterMovies: DrinksAdapter by autoCleared()
    private val viewModel: LookAllFavoritesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentLookAllFavoritesBinding.bind(view)
        viewModel.getAllFavoritesMovie()
        initRecyclerView()
        fillingInTheAdapter()
    }

    private fun initRecyclerView() {
        adapterMovies = DrinksAdapter { favoritesMovie ->
            createDialog(favoritesMovie)
        }
        with(viewBinding.rwFavoritesMovies) {
            adapter = adapterMovies
            setHasFixedSize(true)
            addItemDecoration(MoviesItemDecoration())
        }
    }

    private fun fillingInTheAdapter() {
        viewModel.favoritesMovies.observe(viewLifecycleOwner) { listMovies ->
            adapterMovies.items = listMovies
            viewBinding.twNotSelected.isVisible = listMovies.isEmpty()
        }
    }

    private fun createDialog(favoritesMovie: DrinksData) {
        val title =
            SpannableString("${getString(R.string.do_you_want_delete)} - \n${favoritesMovie.strDrink} ?")

        requireContext().getAlertDialog(
            title = requireContext().getTextWithColor(title, R.color.gray),
            textPositive = R.string.delete,
            textNegative = R.string.cancel,
            background = R.drawable.shape_image_movie
        ) { _, _ ->
            viewModel.removeFavoritesMovie(favoritesMovie.idDrink)
        }
    }
}