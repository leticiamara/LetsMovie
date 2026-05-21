package com.leticiafernandes.movie.presentation.compose

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.leticiafernandes.movie.extensions.formatToReleaseDate
import com.leticiafernandes.movie.presentation.Error
import com.leticiafernandes.movie.presentation.Loading
import com.leticiafernandes.movie.presentation.MoviesViewModel
import com.leticiafernandes.movie.presentation.Success
import com.leticiafernandes.movie.presentation.model.MovieItem

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier,
    onMovieClick: (MovieItem) -> Unit
) {
    val uiState by viewModel.uiState.observeAsState()
    val listState = rememberLazyListState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull() ?: return@derivedStateOf false
            lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 5
        }
    }

    LaunchedEffect(Unit) {
        viewModel.listPopularMovies()
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !viewModel.isLoading) {
            viewModel.listNextPage()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is Loading -> {
                if ((uiState as Loading).loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            is Success -> {
                val movies = (uiState as Success).moviesList
                LazyColumn(state = listState) {
                    items(movies) { item ->
                        if (item is MovieItem) {
                            MovieRow(movie = item, onClick = { onMovieClick(item) })
                        }
                    }
                }
            }
            is Error -> {
                Text(
                    text = (uiState as Error).errorMessage ?: "Error",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {}
        }
    }
}

@Composable
fun MovieRow(movie: MovieItem, onClick: () -> Unit) {
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
            Text(text = movie.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = movie.releaseDate.formatToReleaseDate(), fontSize = 14.sp)
        }
    }
}
