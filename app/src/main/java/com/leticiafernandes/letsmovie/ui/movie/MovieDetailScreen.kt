package com.leticiafernandes.letsmovie.ui.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.extensions.formatToReleaseDate
import com.leticiafernandes.letsmovie.extensions.toMovieAPIImageURL
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import com.leticiafernandes.letsmovie.ui.theme.Dimens
import com.leticiafernandes.letsmovie.ui.theme.Spacing
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.observeAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.movie_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleWatchlist() }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (isBookmarked) {
                                stringResource(R.string.remove_from_favorites)
                            } else {
                                stringResource(R.string.add_to_favorites)
                            },
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = uiState) {
                is ShowMovieLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is ShowMovieInfo -> MovieDetailContent(movie = state.movie)
                is ShowMovieError -> {
                    val message = when (state) {
                        is ShowMovieError.Http -> stringResource(R.string.error_server, state.code)
                        is ShowMovieError.Network -> stringResource(R.string.error_network)
                    }
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
fun MovieDetailContent(movie: MovieItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SubcomposeAsyncImage(
            model = movie.backdropPath?.toMovieAPIImageURL(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.backdropHeight),
            contentScale = ContentScale.Crop,
            loading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium)
        ) {
            AsyncImage(
                model = movie.posterPath?.toMovieAPIImageURL(),
                contentDescription = null,
                modifier = Modifier.size(Dimens.posterWidth, Dimens.posterHeight),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = Spacing.medium)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(Spacing.medium)
                    )
                    Spacer(modifier = Modifier.width(Spacing.extraSmall))
                    Text(
                        text = String.format(Locale.US, "%.1f", movie.voteAverage),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(text = movie.releaseDate.formatToReleaseDate(), style = MaterialTheme.typography.bodyMedium)
            }
        }

        if (!movie.genres.isNullOrEmpty()) {
            InfoSection(label = stringResource(R.string.genres)) {
                Text(
                    text = movie.genres.joinToString(" • ") { it.name },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = Spacing.medium, vertical = Spacing.extraSmall)
                )
            }
        }

        if (movie.originalTitle.isNotBlank() && movie.originalTitle != movie.title) {
            InfoSection(label = stringResource(R.string.original_title)) {
                Text(
                    text = movie.originalTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = Spacing.medium, vertical = Spacing.extraSmall)
                )
            }
        }

        InfoSection(label = stringResource(R.string.original_language)) {
            Text(
                text = Locale.forLanguageTag(movie.originalLanguage).displayLanguage.ifBlank { movie.originalLanguage },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = Spacing.medium, vertical = Spacing.extraSmall)
            )
        }

        Text(
            text = stringResource(R.string.overview),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = Spacing.medium, vertical = Spacing.small)
        )
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = Spacing.medium, end = Spacing.medium, bottom = Spacing.medium)
        )
    }
}

@Composable
private fun InfoSection(label: String, content: @Composable () -> Unit) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = Spacing.medium, vertical = Spacing.small)
    )
    content()
}
