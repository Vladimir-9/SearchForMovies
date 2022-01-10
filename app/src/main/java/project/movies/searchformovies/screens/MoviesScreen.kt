package project.movies.searchformovies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import project.movies.searchformovies.MainActivity
import project.movies.searchformovies.MainActivity.Companion.PATH_LOAD_IMAGE
import project.movies.searchformovies.R
import project.movies.searchformovies.remote.MoviesData
import project.movies.searchformovies.screens.themes.*
import project.movies.searchformovies.screens.viewmodel.MoviesLoadState
import project.movies.searchformovies.screens.viewmodel.MoviesViewModel

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    navController: NavController
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var selectedMovie: MoviesData? by remember { mutableStateOf(null) }

    val getSelectedMovie: (MoviesData) -> Unit = { selectedMovie = it }

    val scope = rememberCoroutineScope()

    val changeStateSheet: () -> Unit = {
        scope.launch {
            if (modalBottomSheetState.isVisible)
                modalBottomSheetState.hide()
            else
                modalBottomSheetState.show()
        }
    }

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetState = modalBottomSheetState,
        sheetBackgroundColor = primaryColor500(),
        sheetContent = {
            ScreenBottomDialog(selectedMovie, viewModel, changeStateSheet)
        }
    ) {
        ScreenMoviesLayout(navController, viewModel, changeStateSheet, getSelectedMovie)
    }
}

@Composable
fun ScreenBottomDialog(
    argument: MoviesData?,
    viewModel: MoviesViewModel,
    changeStateSheet: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.padding(top = 6.dp, bottom = 16.dp),
            painter = painterResource(R.drawable.ic_thick_line),
            contentDescription = null
        )

        Image(
            painter = rememberImagePainter(PATH_LOAD_IMAGE + argument?.backdrop_path),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(10.dp))
        )

        Text(
            text = argument?.description.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.White
        )

        Button(
            onClick = {
                viewModel.saveFavoritesMovie(argument!!)
                changeStateSheet()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = secondaryColor700(),
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(id = R.string.favourites))
        }
    }
}

@Composable
private fun ScreenMoviesLayout(
    navController: NavController,
    viewModel: MoviesViewModel,
    changeStateSheet: () -> Unit,
    getSelectedMovie: (MoviesData) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        backgroundColor = primaryColor500(),
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(MainActivity.ROUT_FAVORITES_MOVIES) },
                backgroundColor = secondaryColor700()
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_favourite),
                    contentDescription = "icon_search"
                )
            }
        }
    ) {
        Column {
            CreateSearchField(viewModel)
            SearchMoviesLayout(scaffoldState, viewModel, changeStateSheet, getSelectedMovie)
        }


    }
}

@Composable
private fun CreateSearchField(viewModel: MoviesViewModel) {
    var text by remember { mutableStateOf("") }

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
                onValueChange = {
                    text = it
                    if (it.isEmpty()) {
                        viewModel.getSearchMovies("")
                    }
                },
                label = { Text(stringResource(id = R.string.search_by_movie_title)) },
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    ButtonSearch(viewModel, text)
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
}

@Composable
private fun SearchMoviesLayout(
    scaffoldState: ScaffoldState,
    viewModel: MoviesViewModel,
    changeStateSheet: () -> Unit,
    getSelectedMovie: (MoviesData) -> Unit
) {
    val isVisibleProgressBar = remember { mutableStateOf(false) }
    ProgressBar(isVisibleProgressBar.value)

    val viewState = viewModel.moviesLiveDate.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    when (val stateMovies = (viewState.value as MoviesLoadState)) {
        is MoviesLoadState.Success -> {
            val listMovies = stateMovies.listMovies

            ListMovies(listMovies, changeStateSheet, getSelectedMovie)
            isVisibleProgressBar.value = false
        }
        is MoviesLoadState.LoadState -> isVisibleProgressBar.value = true

        is MoviesLoadState.Error -> {
            isVisibleProgressBar.value = false
            val errorMassage = stringResource(id = R.string.no_data_available)
            showSnackBar(scaffoldState, coroutineScope, errorMassage)
        }
    }
}

@Composable
private fun ButtonSearch(viewModel: MoviesViewModel, text: String) {
    Button(
        onClick = {
            if (text.isNotEmpty()) {
                viewModel.getSearchMovies(text)
            } else {
                // toast(getString(R.string.enter_movie))
            }
        },
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
}

@Composable
fun ListMovies(
    listMovies: List<MoviesData>,
    changeStateSheet: () -> Unit,
    getSelectedMovie: (MoviesData) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
        listMovies.forEachIndexed() { index, movies ->
            item(index) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 6.dp, end = 6.dp, bottom = 6.dp),
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = primaryColor200(),
                    onClick = {
                        getSelectedMovie(movies)
                        changeStateSheet()
                    }
                ) {
                    ItemMoviesLayout(movies)
                }
            }
        }
    }
}

@Composable
private fun ItemMoviesLayout(movies: MoviesData) {
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
            Text(
                color = textColor(),
                text = stringResource(
                    id = R.string.release,
                    movies.release_date.orEmpty()
                )
            )
        }
    }
}