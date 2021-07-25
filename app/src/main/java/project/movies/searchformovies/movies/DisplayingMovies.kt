package project.movies.searchformovies.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import project.movies.searchformovies.adapter.MoviesAdapter
import project.movies.searchformovies.autoCleared
import project.movies.searchformovies.databinding.DisplayingMoviesFragmentBinding
import project.movies.searchformovies.remote.RemoteMoviesData

class DisplayingMovies : Fragment() {

    private val viewModel: DisplayingMoviesViewModel by viewModels()
    private var viewBinding: DisplayingMoviesFragmentBinding by autoCleared()
    private var adapterMovies: MoviesAdapter by autoCleared()
    private val listMovies = mutableListOf<RemoteMoviesData>(
        RemoteMoviesData("1", "1"),
        RemoteMoviesData("2", "2"),
        RemoteMoviesData("1", "3"),
        RemoteMoviesData("1", "4"),
        RemoteMoviesData("1", "5"),
        RemoteMoviesData("1", "6"),
        RemoteMoviesData("1", "7")
    )

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

        adapterMovies = MoviesAdapter()
        adapterMovies.items = listMovies

        with(viewBinding.rvMovies) {
            adapter = adapterMovies
        }
    }

}