package project.movies.searchformovies.movies

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import project.movies.searchformovies.R
import project.movies.searchformovies.StubClassMovies
import project.movies.searchformovies.adapter.MoviesAdapter
import project.movies.searchformovies.connectivity_info.NetworkChangeReceiver
import project.movies.searchformovies.databinding.DisplayingMoviesFragmentBinding
import project.movies.searchformovies.utility.MoviesItemDecoration
import project.movies.searchformovies.utility.autoCleared
import project.movies.searchformovies.utility.toast

class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()
    private var viewBinding: DisplayingMoviesFragmentBinding by autoCleared()
    private var adapterMovies: MoviesAdapter by autoCleared()
    private var receiver: NetworkChangeReceiver? = null

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
        registerReceiver()
        viewBinding.btSearch.setOnClickListener {
            searchMovies()
        }
        viewBinding.btReloadData.setOnClickListener {
            searchMovies()
        }
    }

    private fun registerReceiver() {
        receiver = NetworkChangeReceiver()
        requireContext().registerReceiver(
            receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun searchMovies() {
        if (viewBinding.etEnterSearch.text.isNotEmpty()) {
            viewModel.getSearchMovies(viewBinding.etEnterSearch.text.toString(), false)
        } else {
            toast(getString(R.string.enter_movie))
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
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.moviesStateFlow.collect {
                    when (it) {
                        is MoviesLoadState.Success -> {
                            adapterMovies.items = it.listMovies
                            visibleElementAfterError(false)
                        }
                        is MoviesLoadState.Error -> {
                            visibleElementAfterError(true)
                        }
                    }
                }
            }
        }
    }

    private fun visibleElementAfterError(isVisible: Boolean) {
        viewBinding.ivError.isVisible = isVisible
        viewBinding.btReloadData.isVisible = isVisible
        viewBinding.rvMovies.isVisible = isVisible.not()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(receiver)
        receiver = null
    }
}