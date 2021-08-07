package project.movies.searchformovies.presentation.detail_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import project.movies.searchformovies.R
import project.movies.searchformovies.databinding.DialogDetailInformationBinding
import project.movies.searchformovies.presentation.adapter.MoviesAdapterDelegate.Companion.PATH_LOAD_IMAGE
import project.movies.searchformovies.utility.autoCleared

@AndroidEntryPoint
class DetailInformationDialog : BottomSheetDialogFragment() {

    private var viewBinding: DialogDetailInformationBinding by autoCleared()
    private val args: DetailInformationDialogArgs by navArgs()
    private val viewModel: DetailInformationViewModel by viewModels()

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