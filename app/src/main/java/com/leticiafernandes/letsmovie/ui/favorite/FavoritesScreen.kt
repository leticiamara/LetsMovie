package com.leticiafernandes.letsmovie.ui.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.leticiafernandes.letsmovie.ui.favorite.FavoritesUiState
import com.leticiafernandes.letsmovie.ui.favorite.FavoritesViewModel
import com.leticiafernandes.letsmovie.ui.favorite.FavoriteMovieItem
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    modifier: Modifier = Modifier,
    onMovieClick: (FavoriteMovieItem) -> Unit
) {
    val uiState by viewModel.uiState.observeAsState(FavoritesUiState.Loading)

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            is FavoritesUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is FavoritesUiState.Empty -> {
                Text(
                    text = "You haven't favorited any movies yet.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            is FavoritesUiState.Content -> {
                FavoritesList(
                    favorites = state.favorites,
                    onMovieClick = onMovieClick,
                    onRemoveClick = { viewModel.toggleFavorite(it.id) }
                )
            }
            is FavoritesUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun FavoritesList(
    favorites: List<FavoriteMovieItem>,
    onMovieClick: (FavoriteMovieItem) -> Unit,
    onRemoveClick: (FavoriteMovieItem) -> Unit
) {
    LazyColumn {
        items(favorites, key = { it.id }) { movie ->
            FavoriteMovieRow(
                movie = movie,
                onClick = { onMovieClick(movie) },
                onRemoveClick = { onRemoveClick(movie) }
            )
        }
    }
}

@Composable
private fun FavoriteMovieRow(
    movie: FavoriteMovieItem,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    val releaseDateLabel = remember(movie.releaseDate) {
        SimpleDateFormat("MMM dd, yyyy", Locale.US).format(movie.releaseDate) ?: ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
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
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = movie.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = releaseDateLabel, fontSize = 14.sp)
            Text(
                text = "Rating: ${"%.1f".format(movie.voteAverage)}",
                fontSize = 14.sp
            )
        }
        IconButton(onClick = onRemoveClick) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Remove from favorites",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

