package com.leticiafernandes.letsmovie.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.ui.watchlist.WatchListScreen
import com.leticiafernandes.letsmovie.ui.watchlist.WatchlistViewModel
import com.leticiafernandes.letsmovie.ui.movie.MoviesScreen
import com.leticiafernandes.letsmovie.ui.movie.MoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Long) -> Unit,
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    watchlistViewModel: WatchlistViewModel = hiltViewModel()
) {
    var currentTab by remember { mutableStateOf(HomeTab.Popular) }
    val selectedIndex = if (currentTab == HomeTab.Popular) 0 else 1

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when (currentTab) {
                        HomeTab.Popular -> stringResource(R.string.title_movies)
                        HomeTab.Watchlist -> stringResource(R.string.title_watchlist)
                    }
                    Text(text = title)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            SecondaryTabRow(
                selectedTabIndex = selectedIndex,
                modifier = Modifier.navigationBarsPadding(),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                divider = {},
                indicator = {
                    Box(
                        modifier = Modifier
                            .tabIndicatorLayout { measurable, constraints, tabPositions ->
                                val tab = tabPositions[selectedIndex]
                                val placeable = measurable.measure(
                                    constraints.copy(
                                        minWidth = tab.width.roundToPx(),
                                        maxWidth = tab.width.roundToPx()
                                    )
                                )
                                layout(constraints.maxWidth, constraints.maxHeight) {
                                    placeable.place(tab.left.roundToPx(), 0)
                                }
                            }
                            .height(3.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(bottomStart = 3.dp, bottomEnd = 3.dp)
                            )
                    )
                }
            ) {
                Tab(
                    selected = currentTab == HomeTab.Popular,
                    onClick = { currentTab = HomeTab.Popular },
                    icon = { Icon(Icons.Default.Movie, contentDescription = null) },
                    text = { Text(stringResource(R.string.title_movies)) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Tab(
                    selected = currentTab == HomeTab.Watchlist,
                    onClick = { currentTab = HomeTab.Watchlist },
                    icon = { Icon(Icons.Default.Bookmark, contentDescription = null) },
                    text = { Text(stringResource(R.string.title_watchlist)) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        when (currentTab) {
            HomeTab.Popular -> MoviesScreen(
                viewModel = moviesViewModel,
                modifier = modifier,
                onMovieClick = { movie -> onMovieClick(movie.id) }
            )
            HomeTab.Watchlist -> WatchListScreen(
                viewModel = watchlistViewModel,
                modifier = modifier,
                onMovieClick = { favorite -> onMovieClick(favorite.id) }
            )
        }
    }
}

enum class HomeTab {
    Popular, Watchlist,
}
