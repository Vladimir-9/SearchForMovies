package project.movies.searchformovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import project.movies.searchformovies.presentation.look_all_favorites.LookAllFavoritesViewModel
import project.movies.searchformovies.presentation.movies_main.MoviesViewModel
import project.movies.searchformovies.screens.FavoritesMoviesScreen
import project.movies.searchformovies.screens.MoviesScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModelMovies: MoviesViewModel by viewModels()
    private val viewModelFavorites: LookAllFavoritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold {
                NavHost(navController, "mainMovies") {
                    composable(ROUT_MAIN_MOVIES) {
                        MoviesScreen(viewModelMovies, navController)
                    }
                    composable(ROUT_FAVORITES_MOVIES) {
                        FavoritesMoviesScreen(viewModelFavorites)
                    }
                }
            }
        }
    }

    companion object {
        const val PATH_LOAD_IMAGE = "https://image.tmdb.org/t/p/w500"
        const val ROUT_MAIN_MOVIES = "mainMovies"
        const val ROUT_FAVORITES_MOVIES = "favoritesMovies"
        private const val KEY_MOVIES = "keyMovie"
    }
}