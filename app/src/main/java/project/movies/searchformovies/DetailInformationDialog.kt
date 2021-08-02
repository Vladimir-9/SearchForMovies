package project.movies.searchformovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import project.movies.searchformovies.adapter.MoviesAdapterDelegate.Companion.PATH_LOAD_IMAGE
import project.movies.searchformovies.databinding.DialogDetailInformationBinding
import timber.log.Timber

class DetailInformationDialog : BottomSheetDialogFragment() {

    private lateinit var viewBinding: DialogDetailInformationBinding
    private val args: DetailInformationDialogArgs by navArgs()

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

        Timber.e("${PATH_LOAD_IMAGE + args.uriPoster} \n  ${args.description}")
        viewBinding.ivMovie.clipToOutline = true

        Glide.with(view)
            .load(PATH_LOAD_IMAGE + args.uriPoster)
            .placeholder(R.drawable.ic_movie)
            .error(R.drawable.ic_not_poster)
            .into(viewBinding.ivMovie)
        viewBinding.twDescriptionMovie.text = args.description

    }
}