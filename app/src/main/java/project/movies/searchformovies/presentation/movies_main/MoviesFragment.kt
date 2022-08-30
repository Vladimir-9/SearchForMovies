package project.movies.searchformovies.presentation.movies_main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.leinardi.android.speeddial.SpeedDialActionItem
import dagger.hilt.android.AndroidEntryPoint
import project.movies.searchformovies.R
import project.movies.searchformovies.databinding.FragmentMoviesBinding
import project.movies.searchformovies.presentation.adapter.MoviesAdapter
import project.movies.searchformovies.utility.MoviesItemDecoration
import project.movies.searchformovies.utility.autoCleared
import project.movies.searchformovies.utility.toast

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val viewModel: MoviesViewModel by viewModels()
    private var viewBinding: FragmentMoviesBinding by autoCleared()
    private var adapterMovies: MoviesAdapter by autoCleared()
    private var responseMovies = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMoviesBinding.bind(view)
        collectingRemoteMovies()
        initRecyclerView()
        fabInit()
        fabReactionToTheClick()
        viewBinding.etEnterSearch.doAfterTextChanged {
            if (it?.isEmpty() == true) {
                viewModel.getSearchMovies("")
            }
        }
        viewBinding.btSearch.setOnClickListener {
            searchMovies()
            hideKeyboard()
        }
        viewBinding.btReloadData.setOnClickListener {
            searchMovies()
        }
    }

    private fun searchMovies() {
        val searchQuestion = viewBinding.etEnterSearch.text.toString()
        if (searchQuestion.isNotEmpty() && responseMovies != searchQuestion) {
            responseMovies = viewBinding.etEnterSearch.text.toString()
            viewModel.getSearchMovies(viewBinding.etEnterSearch.text.toString())
        } else {
            requireContext().toast(getString(R.string.enter_movie))
        }
    }

    private fun initRecyclerView() {
        adapterMovies = MoviesAdapter { movies ->
            val action = MoviesFragmentDirections.actionDisplayingMoviesToDetailMoviesDialog(
                movies
            )
            findNavController().navigate(action)
        }
        with(viewBinding.rvMovies) {
            adapter = adapterMovies
            setHasFixedSize(true)
            addItemDecoration(MoviesItemDecoration())
        }
    }

    private fun collectingRemoteMovies() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            viewBinding.progressBar.isVisible = it.isLoading
            isEnableButton(it.isLoading.not())

            if (it.listMovies.isNotEmpty())
                adapterMovies.items = it.listMovies

            visibleElementAfterError(it.error.isNotEmpty())
        }
    }

    private fun isEnableButton(isEnabled: Boolean) {
        with(viewBinding) {
            btSearch.isEnabled = isEnabled
            btReloadData.isEnabled = isEnabled
        }
    }

    private fun hideKeyboard() {
        val imm =
            activity?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(viewBinding.constraintLayout.windowToken, 0)
    }

    private fun visibleElementAfterError(isVisible: Boolean) {
        with(viewBinding) {
            ivError.isVisible = isVisible
            btReloadData.isVisible = isVisible
            rvMovies.isVisible = isVisible.not()
        }
    }

    private fun fabInit() {
        viewBinding.fab.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_favorites, R.drawable.ic_favourite)
                .setTheme(R.style.Theme_Fab_Favorites)
                .create()
        )
    }

    private fun fabReactionToTheClick() {
        viewBinding.fab.setOnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.fab_favorites -> findNavController().navigate(R.id.action_displayingMovies_to_lookAllFavoritesFragment)
            }
            false
        }
    }
}