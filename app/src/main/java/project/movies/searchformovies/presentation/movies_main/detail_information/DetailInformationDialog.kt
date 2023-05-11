package project.movies.searchformovies.presentation.movies_main.detail_information

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
import project.movies.searchformovies.data.mapper.toMoviesEntity
import project.movies.searchformovies.databinding.DialogDetailInformationBinding
import project.movies.searchformovies.domain.model.DataDetailsDrink
import project.movies.searchformovies.utility.Resource
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

        observeViewModel()
        viewModel.detailDesc(args.drinksData.idDrink)

        viewBinding.btFavourites.setOnClickListener {
            saveFavoritesMovie()
        }
    }

    private fun observeViewModel() {
        viewModel.detailDesc.observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Success -> {

                    val detailDesc = res.data?.get(0)

                    Glide.with(this)
                        .load(detailDesc?.strDrinkThumb)
                        .placeholder(R.drawable.ic_movie)
                        .error(R.drawable.ic_not_poster)
                        .into(viewBinding.ivMovie)

                    viewBinding.twTitle.text = detailDesc?.strDrink
                    viewBinding.twDescriptionMovie.text = detailDesc?.strInstructions
                    viewBinding.twIngredients.text = getAllIngredients(detailDesc)

                }
                is Resource.Error -> Unit
            }
        }
    }

    private fun getAllIngredients(ingr: DataDetailsDrink?): String {
        if (ingr == null) return ""

        val str = StringBuilder()

        ingr.allIngredientsList.forEach { ingredient ->
            if (ingredient.isNotBlank()) str.append(" - $ingredient \n")
        }

        return str.toString()
    }

    private fun saveFavoritesMovie() {
        viewModel.saveFavoritesMovie(args.drinksData.toMoviesEntity())
        findNavController().popBackStack()
    }
}