package project.movies.searchformovies.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.movies.searchformovies.R
import project.movies.searchformovies.remote.MoviesData
import project.movies.searchformovies.screens.themes.primaryColor200
import project.movies.searchformovies.screens.themes.primaryColor500
import project.movies.searchformovies.screens.themes.secondaryColor200
import project.movies.searchformovies.screens.viewmodel.FavoritesMoviesViewModel

@Composable
fun FavoritesMoviesScreen(favoritesMoviesViewModel: FavoritesMoviesViewModel) {

    favoritesMoviesViewModel.getAllFavoritesMovie()

    val openDialog = remember { mutableStateOf(false) }
    var selectedMovie: MoviesData? by remember { mutableStateOf(null) }

    val click: (movie: MoviesData) -> Unit = {
        openDialog.value = true
        selectedMovie = it
    }

    val listFavorites = favoritesMoviesViewModel.favoritesMoviesStateFlow.collectAsState()

    Surface(color = primaryColor500(), modifier = Modifier.fillMaxSize()) {
        CreateDialog(openDialog, selectedMovie, favoritesMoviesViewModel)

        if (listFavorites.value.isNotEmpty())
            ListMovies(listFavorites.value, {}, { movie ->
                click(movie)
            })
        else
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.did_not_choose_anything),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
    }
}

@Composable
private fun CreateDialog(
    openDialog: MutableState<Boolean>,
    selectedMovie: MoviesData?,
    favoritesMoviesViewModel: FavoritesMoviesViewModel
) {
    if (openDialog.value)
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            backgroundColor = primaryColor200(),
            title = {
                Text(
                    text = stringResource(
                        id = R.string.want_to_delete,
                        selectedMovie?.title.orEmpty()
                    ),
                    color = Color.White
                )
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel),
                        modifier = Modifier
                            .clickable { openDialog.value = false }
                            .padding(10.dp),
                        color = secondaryColor200()
                    )

                    Text(
                        text = stringResource(id = R.string.delete),
                        modifier = Modifier
                            .clickable {
                                selectedMovie?.id?.let {
                                    favoritesMoviesViewModel.removeFavoritesMovie(it)
                                }
                                openDialog.value = false
                            }
                            .padding(10.dp),
                        color = secondaryColor200()
                    )
                }
            }
        )
}