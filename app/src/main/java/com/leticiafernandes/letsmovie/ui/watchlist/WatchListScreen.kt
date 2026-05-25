package com.leticiafernandes.letsmovie.ui.watchlist

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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.extensions.toMovieAPIImageURL
import com.leticiafernandes.letsmovie.ui.theme.Dimens
import com.leticiafernandes.letsmovie.ui.theme.Spacing
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun WatchListScreen(
    viewModel: WatchlistViewModel,
    modifier: Modifier = Modifier,
    onMovieClick: (FavoriteMovieItem) -> Unit
) {
    val uiState by viewModel.uiState.observeAsState(WatchlistUiState.Loading)

    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            is WatchlistUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is WatchlistUiState.Empty -> {
                Text(
                    text = stringResource(R.string.favorites_empty),
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            is WatchlistUiState.Content -> {
                FavoritesList(
                    favorites = state.favorites,
                    onMovieClick = onMovieClick,
                    onRemoveClick = { viewModel.toggleFavorite(it.id) }
                )
            }
            is WatchlistUiState.Error -> {
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

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.medium, vertical = Spacing.small),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation)
    ) {
        Row(
            modifier = Modifier.padding(Spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = movie.posterPath?.toMovieAPIImageURL(),
                contentDescription = null,
                modifier = Modifier.size(Dimens.posterWidth, Dimens.posterHeight),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = Spacing.small)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.titleSmall)
                Text(text = releaseDateLabel, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = stringResource(R.string.rating_format, String.format(java.util.Locale.US, "%.1f", movie.voteAverage)),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onRemoveClick) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = stringResource(R.string.remove_from_favorites),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}
