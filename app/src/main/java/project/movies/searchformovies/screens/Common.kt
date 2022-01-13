package project.movies.searchformovies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import project.movies.searchformovies.R

@Composable
fun ProgressBar(isVisible: Boolean) {
    if (isVisible)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(70.dp),
                color = Color.Blue,
                strokeWidth = 7.dp
            )
        }
}

fun showSnackBar(scaffoldState: ScaffoldState, scope: CoroutineScope, message: String) {
    scope.launch {
        scaffoldState.snackbarHostState.showSnackbar(message)
    }
}

@Composable
fun Reload(isVisible: Boolean, click: () -> Unit) {
    if (isVisible)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { click() },
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_icon_refresh_1),
                    contentDescription = null
                )
            }
        }
}