package project.movies.searchformovies.presentation.look_all_favorites

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import project.movies.searchformovies.R
import project.movies.searchformovies.viewpager.CurveTransformer
import project.movies.searchformovies.viewpager.ViewPagerLayoutManager
import project.movies.searchformovies.databinding.FragmentLookAllFavoritesBinding
import project.movies.searchformovies.presentation.adapter.MoviesAdapter
import project.movies.searchformovies.remote.MoviesData
import project.movies.searchformovies.utility.MoviesItemDecoration
import project.movies.searchformovies.utility.autoCleared
import kotlin.math.roundToInt

@AndroidEntryPoint
class LookAllFavoritesFragment : Fragment(R.layout.fragment_look_all_favorites) {

    private var viewBinding: FragmentLookAllFavoritesBinding by autoCleared()
    private var adapterMovies: MoviesAdapter by autoCleared()
    private val viewModel: LookAllFavoritesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentLookAllFavoritesBinding.bind(view)
        viewModel.getAllFavoritesMovie()
        initRecyclerView()
        fillingInTheAdapter()
    }

    private fun initRecyclerView() {
        val displayMetrics = requireContext().resources.displayMetrics
        val width: Int = (displayMetrics.widthPixels * 0.8f).roundToInt()
        val height: Int = (displayMetrics.heightPixels * 0.8f).roundToInt()

        adapterMovies = MoviesAdapter(width, height) { favoritesMovie ->
            createDialog(favoritesMovie)
        }
        with(viewBinding.rwFavoritesMovies) {
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

    private fun fillingInTheAdapter() {
        lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoritesMoviesStateFlow.collect { listMovies ->
                    adapterMovies.items = listMovies
                    viewBinding.twNotSelected.isVisible = listMovies.isEmpty()
                }
            }
        }
    }

    private fun createDialog(favoritesMovie: MoviesData) {
        val title = setTheTitleColor(favoritesMovie)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setBackground(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.shape_image_movie
                )
            )
            .setPositiveButton("??????????????") { _, _ ->
                viewModel.removeFavoritesMovie(favoritesMovie.id)
            }
            .setNegativeButton("????????????") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun setTheTitleColor(favoritesMovie: MoviesData): SpannableString {
        val title = SpannableString("???? ???????????? ?????????????? - \n${favoritesMovie.title} ?")
        val getColor = ResourcesCompat.getColor(requireContext().resources, R.color.gray, null)
        title.setSpan(
            ForegroundColorSpan(getColor),
            0,
            title.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return title
    }
}