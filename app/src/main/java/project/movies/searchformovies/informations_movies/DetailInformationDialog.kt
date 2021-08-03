package project.movies.searchformovies.informations_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import project.movies.searchformovies.R
import project.movies.searchformovies.adapter.MoviesAdapterDelegate.Companion.PATH_LOAD_IMAGE
import project.movies.searchformovies.databinding.DialogDetailInformationBinding
import project.movies.searchformovies.movies.MoviesViewModel

class DetailInformationDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: DialogDetailInformationBinding
    private val args: DetailInformationDialogArgs by navArgs()
    private val viewModel: MoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DialogDetailInformationBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.ivMovie.clipToOutline = true
        Glide.with(view)
            .load(PATH_LOAD_IMAGE + args.movie.backdrop_path)
            .placeholder(R.drawable.ic_movie)
            .error(R.drawable.ic_not_poster)
            .into(viewBinding.ivMovie)
        viewBinding.twDescriptionMovie.text = args.movie.description

        viewBinding.btFavourites.setOnClickListener {
            saveFavoritesMovie()
        }
    }

    private fun saveFavoritesMovie() {
        viewModel.saveFavoritesMovie(args.movie)
        findNavController().popBackStack()
    }
}