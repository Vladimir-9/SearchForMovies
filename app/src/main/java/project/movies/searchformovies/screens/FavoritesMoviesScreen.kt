package project.movies.searchformovies.screens

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import project.movies.searchformovies.presentation.look_all_favorites.LookAllFavoritesViewModel
import project.movies.searchformovies.screens.themes.primaryColor500

@Composable
fun FavoritesMoviesScreen(viewModelFavorites: LookAllFavoritesViewModel) {

    viewModelFavorites.getAllFavoritesMovie()

    val listFavorites = viewModelFavorites.favoritesMoviesStateFlow.collectAsState()
    Surface(color = primaryColor500()){

        ListMovies(listFavorites.value, {}, {})
    }
}