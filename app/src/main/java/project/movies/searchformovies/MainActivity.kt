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
import project.movies.searchformovies.screens.viewmodel.FavoritesMoviesViewModel
import project.movies.searchformovies.screens.viewmodel.MoviesViewModel
import project.movies.searchformovies.screens.FavoritesMoviesScreen
import project.movies.searchformovies.screens.MoviesScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModelMovies: MoviesViewModel by viewModels()
    private val viewModelFavoritesMovies: FavoritesMoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(500)
        setTheme(R.style.Theme_SearchForMovies)
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold {
                NavHost(navController, "mainMovies") {
                    composable(ROUT_MAIN_MOVIES) {
                        MoviesScreen(viewModelMovies, navController)
                    }
                    composable(ROUT_FAVORITES_MOVIES) {
                        FavoritesMoviesScreen(viewModelFavoritesMovies)
                    }
                }
            }
        }
    }

    companion object {
        const val PATH_LOAD_IMAGE = "https://image.tmdb.org/t/p/w500"
        const val ROUT_MAIN_MOVIES = "mainMovies"
        const val ROUT_FAVORITES_MOVIES = "favoritesMovies"
    }
}