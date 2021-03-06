package project.movies.searchformovies.presentation.movies_main

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.leinardi.android.speeddial.SpeedDialActionItem
import dagger.hilt.android.AndroidEntryPoint
import project.movies.searchformovies.R
import project.movies.searchformovies.viewpager.CurveTransformer
import project.movies.searchformovies.viewpager.ViewPagerLayoutManager
import project.movies.searchformovies.connectivity_info.NetworkChangeReceiver
import project.movies.searchformovies.databinding.FragmentMoviesBinding
import project.movies.searchformovies.presentation.adapter.MoviesAdapter
import project.movies.searchformovies.utility.MoviesItemDecoration
import project.movies.searchformovies.utility.autoCleared
import project.movies.searchformovies.utility.toast
import kotlin.math.roundToInt

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val viewModel: MoviesViewModel by viewModels()
    private var viewBinding: FragmentMoviesBinding by autoCleared()
    private var adapterMovies: MoviesAdapter by autoCleared()
    private var receiver: NetworkChangeReceiver? = null
    private var responseMovies = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMoviesBinding.bind(view)
        collectingRemoteMovies()
        initRecyclerView()
        listenerSearchQuery()
        registerReceiver()
        fabInit()
        fabReactionToTheClick()
        viewBinding.btSearch.setOnClickListener {
            searchMovies()
            hideKeyboard()
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
        val searchQuestion = viewBinding.etEnterSearch.text.toString()
        if (searchQuestion.isNotEmpty() && responseMovies != searchQuestion) {
            responseMovies = viewBinding.etEnterSearch.text.toString()
            viewModel.getSearchMovies(viewBinding.etEnterSearch.text.toString())
        } else {
            toast(getString(R.string.enter_movie))
        }
    }

    private fun initRecyclerView() {
        val displayMetrics = requireContext().resources.displayMetrics
        val width: Int = (displayMetrics.widthPixels * 0.8f).roundToInt()
        val height: Int = (displayMetrics.heightPixels * 0.8f).roundToInt()

        adapterMovies = MoviesAdapter(width, height) { movies ->
            val action = MoviesFragmentDirections.actionDisplayingMoviesToDetailMoviesDialog(
                movies
            )
            findNavController().navigate(action)
        }
        with(viewBinding.rvMovies) {
            setFlingAble(false)
            val layoutManagerGallery =
                ViewPagerLayoutManager(ViewPagerLayoutManager.HORIZONTAL)

            layoutManagerGallery.attach(this)
            layoutManagerGallery.setItemTransformer(CurveTransformer())

            adapter = adapterMovies
            setHasFixedSize(true)
            addItemDecoration(MoviesItemDecoration())
        }
    }

    private fun listenerSearchQuery() {
        viewBinding.etEnterSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty()) {
                    viewModel.getSearchMovies("")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun collectingRemoteMovies() {
        viewModel.moviesLiveDate.observe(viewLifecycleOwner) {
            when (it) {
                is MoviesLoadState.Success -> {
                    adapterMovies.items = it.listMovies
                    visibleElementAfterError(false)
                    isEnableButton(true)
                    viewBinding.progressBar.isVisible = false
                }
                is MoviesLoadState.Error -> {
                    visibleElementAfterError(true)
                    isEnableButton(true)
                    viewBinding.progressBar.isVisible = false
                }
                is MoviesLoadState.LoadState -> {
                    viewBinding.progressBar.isVisible = true
                    viewBinding.rvMovies.isVisible = false
                    isEnableButton(false)
                }
            }
        }
    }

    private fun isEnableButton(isEnabled: Boolean) {
        viewBinding.btSearch.isEnabled = isEnabled
        viewBinding.btReloadData.isEnabled = isEnabled
    }

    private fun hideKeyboard() {
        val imm =
            activity?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(viewBinding.constraintLayout.windowToken, 0)
    }

    private fun visibleElementAfterError(isVisible: Boolean) {
        viewBinding.ivError.isVisible = isVisible
        viewBinding.btReloadData.isVisible = isVisible
        viewBinding.rvMovies.isVisible = isVisible.not()
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

    override fun onDestroy() {
        super.onDestroy()
        if (receiver != null)
            requireContext().unregisterReceiver(receiver)
        receiver = null
    }
}