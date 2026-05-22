package com.leticiafernandes.letsmovie.ui.movie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.leticiafernandes.letsmovie.extensions.formatToReleaseDate
import com.leticiafernandes.letsmovie.extensions.toMovieAPIImageURL
import com.leticiafernandes.letsmovie.ui.movie.model.MovieItem
import com.leticiafernandes.letsmovie.ui.theme.Dimens
import com.leticiafernandes.letsmovie.ui.theme.Spacing

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier,
    onMovieClick: (MovieItem) -> Unit
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()

    Box(modifier = modifier.fillMaxSize()) {
        when (val refresh = movies.loadState.refresh) {
            is LoadState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is LoadState.Error -> Text(
                text = refresh.error.message ?: "Error loading movies",
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.error
            )
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    contentPadding = PaddingValues(Spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(Spacing.medium),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.medium),
                    modifier = Modifier.verticalScrollbar(gridState)
                ) {
                    items(count = movies.itemCount) { index ->
                        val movie = movies[index]
                        if (movie != null) {
                            MovieCard(movie = movie, onClick = { onMovieClick(movie) })
                        }
                    }
                    if (movies.loadState.append is LoadState.Loading) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(Spacing.medium),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.verticalScrollbar(
    state: LazyGridState,
    width: Dp = 4.dp,
    color: Color = Color.Gray
): Modifier = this.composed {
    val alpha by animateFloatAsState(
        targetValue = if (state.isScrollInProgress) 1f else 0f,
        animationSpec = tween(durationMillis = if (state.isScrollInProgress) 100 else 700),
        label = "scrollbar_alpha"
    )

    drawWithContent {
        drawContent()

        val totalItems = state.layoutInfo.totalItemsCount
        val visibleItems = state.layoutInfo.visibleItemsInfo.size

        if (totalItems > visibleItems && alpha > 0f) {
            val thumbHeight = (size.height * visibleItems / totalItems.toFloat())
                .coerceAtLeast(40.dp.toPx())
            val scrollFraction = state.firstVisibleItemIndex.toFloat() /
                (totalItems - visibleItems).toFloat()
            val thumbY = ((size.height - thumbHeight) * scrollFraction).coerceIn(0f, size.height - thumbHeight)

            drawRoundRect(
                color = color,
                topLeft = Offset(size.width - width.toPx() - 4.dp.toPx(), thumbY),
                size = Size(width.toPx(), thumbHeight),
                cornerRadius = CornerRadius(width.toPx() / 2),
                alpha = alpha
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(movie: MovieItem, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            AsyncImage(
                model = movie.posterPath?.toMovieAPIImageURL(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.backdropHeight),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(Spacing.medium)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.releaseDate.formatToReleaseDate(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
