package com.leticiafernandes.letsmovie.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.leticiafernandes.letsmovie.infrastructure.model.Movie
import com.leticiafernandes.letsmovie.presentation.presenter.FavouriteMoviesPresenter
import com.leticiafernandes.letsmovie.presentation.view.mvpview.IFavouriteMvpView

@Composable
fun FavouriteMoviesScreen(
    modifier: Modifier = Modifier,
    onMovieClick: (Movie) -> Unit
) {
    val context = LocalContext.current
    var moviesListState by remember { mutableStateOf<List<Movie>>(emptyList()) }
    
    val presenter = remember {
        FavouriteMoviesPresenter(context, object : IFavouriteMvpView {
            override fun listMovies(movies: List<Movie>?) {
                moviesListState = movies ?: emptyList()
            }
            override fun showMessage(resId: Int) {}
        })
    }

    DisposableEffect(Unit) {
        presenter.listAllFavouriteMovies()
        onDispose { }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (moviesListState.isEmpty()) {
            Text(
                text = "No favourite movies yet",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn {
                items(moviesListState) { movie ->
                    FavouriteMovieRow(movie = movie, onClick = { onMovieClick(movie) })
                }
            }
        }
    }
}

@Composable
fun FavouriteMovieRow(movie: Movie, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = null,
            modifier = Modifier.size(100.dp, 150.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = movie.title ?: "", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = movie.releaseDate?.toString() ?: "", fontSize = 14.sp)
        }
    }
}
