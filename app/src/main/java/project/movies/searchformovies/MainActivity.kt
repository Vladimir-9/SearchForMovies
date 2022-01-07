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
import project.movies.searchformovies.presentation.movies_main.MoviesViewModel
import project.movies.searchformovies.screens.MoviesScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold {
                NavHost(navController, "mainMovies") {
                    composable("mainMovies") {
                        MoviesScreen(viewModel)
                    }
                }
            }
        }
    }

    companion object {
        const val PATH_LOAD_IMAGE = "https://image.tmdb.org/t/p/w500"
    }
}