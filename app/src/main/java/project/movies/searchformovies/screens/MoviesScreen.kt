package project.movies.searchformovies.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import project.movies.searchformovies.MainActivity.Companion.PATH_LOAD_IMAGE
import project.movies.searchformovies.R
import project.movies.searchformovies.presentation.movies_main.MoviesLoadState
import project.movies.searchformovies.presentation.movies_main.MoviesViewModel
import project.movies.searchformovies.remote.MoviesData
import project.movies.searchformovies.screens.themes.*

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel
) {
    val childNavController = rememberNavController()
    var text by remember { mutableStateOf("") }
    Scaffold(backgroundColor = Color(0xff0d253f),
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                backgroundColor = secondaryColor700()
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_done),
                    contentDescription = "icon_search"
                )
            }
        }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .height(56.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        value = text,
                        onValueChange = { text = it },
                        label = { Text(stringResource(id = R.string.search_by_movie_title)) },
                        shape = RoundedCornerShape(16.dp),
                        trailingIcon = {
                            ButtonSearch()
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            focusedLabelColor = grey(),
                            cursorColor = Color.DarkGray,
                            focusedIndicatorColor = Color.Transparent,
                            backgroundColor = greyDark(),
                        )
                    )
                }
            }

            val viewState = viewModel.moviesLiveDate.observeAsState()

            when (viewState.value) {
                is MoviesLoadState.Success -> ItemMovies((viewState.value as MoviesLoadState.Success).listMovies)
            }
        }
    }
}

@Composable
private fun ButtonSearch() = Button(
    onClick = { /*TODO*/ },
    modifier = Modifier
        .fillMaxHeight(),
    colors = ButtonDefaults.outlinedButtonColors(contentColor = grey()),
    shape = RoundedCornerShape(16.dp),
) {
    Image(
        painter = painterResource(R.drawable.ic_search),
        contentDescription = "icon_search"
    )
}

@Composable
private fun ItemMovies(listMovies: List<MoviesData>) =
    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
        listMovies.forEachIndexed() { index, movies ->
            item(index) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 6.dp, end = 6.dp, bottom = 6.dp),
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = primaryColor200(),
                    onClick = { Log.e("sdfs", "onClick") }
                ) {
                    Row(
                        modifier = Modifier
                            .height(170.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = rememberImagePainter(PATH_LOAD_IMAGE + movies.poster_path),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .width(130.dp)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(10.dp))
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(start = 16.dp),
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                color = textColor(),
                                text = movies.title,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 2,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                color = textColor(),
                                text = movies.description,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 6
                            )
                            Text(color = textColor(), text = movies.release_date.orEmpty())
                        }
                    }
                }
            }
        }
    }