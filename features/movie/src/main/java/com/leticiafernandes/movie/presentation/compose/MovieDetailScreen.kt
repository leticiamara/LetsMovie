package com.leticiafernandes.movie.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.leticiafernandes.movie.extensions.formatToReleaseDate
import com.leticiafernandes.movie.extensions.toMovieAPIImageURL
import com.leticiafernandes.movie.presentation.detail.MovieDetailViewModel
import com.leticiafernandes.movie.presentation.detail.ShowMovieInfo
import com.leticiafernandes.movie.presentation.model.MovieItem

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
                title = { Text(text = "Teste") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (uiState) {
                is ShowMovieInfo -> {
                    val movie = (uiState as ShowMovieInfo).movie
                    MovieDetailContent(movie = movie)
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
        AsyncImage(
            model = movie.backdropPath?.toMovieAPIImageURL(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = movie.posterPath?.toMovieAPIImageURL(),
                contentDescription = null,
                modifier = Modifier.size(100.dp, 150.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = movie.title, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text(text = "Rating: ${movie.voteAverage}", fontSize = 16.sp)
                Text(text = movie.releaseDate.formatToReleaseDate(), fontSize = 14.sp)
            }
        }
        Text(
            text = "Overview",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text(
            text = movie.overview,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
    }
}
