package project.movies.searchformovies.movies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import project.movies.searchformovies.adapter.MoviesAdapter
import project.movies.searchformovies.databinding.DisplayingMoviesFragmentBinding
import project.movies.searchformovies.utility.MoviesItemDecoration
import project.movies.searchformovies.utility.autoCleared
import project.movies.searchformovies.utility.toast

class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private var viewBinding: DisplayingMoviesFragmentBinding by autoCleared()
    private var adapterMovies: MoviesAdapter by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DisplayingMoviesFragmentBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectingRemoteMovies()
        initRecyclerView()
        listenerSearchQuery()
        searchQuery()
    }

    private fun searchQuery() {
        viewBinding.ibSearch.setOnClickListener {
            viewModel.getSearchMovies(viewBinding.etEnterSearch.text.toString(), false)
        }
    }

    private fun initRecyclerView() {
        adapterMovies = MoviesAdapter()
        with(viewBinding.rvMovies) {
            adapter = adapterMovies
            addItemDecoration(MoviesItemDecoration())
        }
    }

    private fun listenerSearchQuery() {
        viewBinding.etEnterSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty()) {
                    viewModel.getSearchMovies("", true)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun collectingRemoteMovies() {
        lifecycleScope.launchWhenStarted {
            viewModel.moviesStateFlow.collect {
                when (it) {
                    is MoviesLoadState.Success -> adapterMovies.items = it.listMovies
                    is MoviesLoadState.Error -> toast("Ошибка")
                }
            }
        }
    }
}