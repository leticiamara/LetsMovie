package com.leticiafernandes.letsmovie.ui.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.extensions.formatToReleaseDate
import com.leticiafernandes.letsmovie.extensions.toMovieAPIImageURL
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import com.leticiafernandes.letsmovie.ui.theme.Dimens
import com.leticiafernandes.letsmovie.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.movie_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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
                is ShowMovieInfo -> MovieDetailContent(movie = state.movie)
                is ShowMovieError -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
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
        AsyncImage(
            model = movie.backdropPath?.toMovieAPIImageURL(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.backdropHeight),
            contentScale = ContentScale.Crop
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
                Text(
                    text = stringResource(R.string.rating_format, "%.1f".format(movie.voteAverage)),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(text = movie.releaseDate.formatToReleaseDate(), style = MaterialTheme.typography.bodyMedium)
            }
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
