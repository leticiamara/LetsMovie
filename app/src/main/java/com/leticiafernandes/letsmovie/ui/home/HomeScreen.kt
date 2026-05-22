package com.leticiafernandes.letsmovie.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.ui.favorite.FavoritesScreen
import com.leticiafernandes.letsmovie.ui.favorite.FavoritesViewModel
import com.leticiafernandes.letsmovie.ui.movie.MoviesScreen
import com.leticiafernandes.letsmovie.ui.movie.MoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Long) -> Unit
) {
    var currentTab by remember { mutableStateOf(HomeTab.Popular) }
    val selectedIndex = if (currentTab == HomeTab.Popular) 0 else 1

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when (currentTab) {
                        HomeTab.Popular -> stringResource(R.string.title_populars)
                        HomeTab.Favourite -> stringResource(R.string.title_favourites)
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
            TabRow(
                selectedTabIndex = selectedIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                divider = {},
                indicator = { tabPositions ->
                    val tab = tabPositions[selectedIndex]
                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(x = tab.left)
                                .width(tab.width)
                                .height(3.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(bottomStart = 3.dp, bottomEnd = 3.dp)
                                )
                        )
                    }
                }
            ) {
                Tab(
                    selected = currentTab == HomeTab.Popular,
                    onClick = { currentTab = HomeTab.Popular },
                    icon = { Icon(Icons.Default.Movie, contentDescription = null) },
                    text = { Text(stringResource(R.string.title_populars)) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Tab(
                    selected = currentTab == HomeTab.Favourite,
                    onClick = { currentTab = HomeTab.Favourite },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                    text = { Text(stringResource(R.string.title_favourites)) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        when (currentTab) {
            HomeTab.Popular -> {
                val moviesViewModel: MoviesViewModel = hiltViewModel()
                MoviesScreen(
                    viewModel = moviesViewModel,
                    modifier = modifier,
                    onMovieClick = { movie -> onMovieClick(movie.id) }
                )
            }
            HomeTab.Favourite -> {
                val favoritesViewModel: FavoritesViewModel = hiltViewModel()
                FavoritesScreen(
                    viewModel = favoritesViewModel,
                    modifier = modifier,
                    onMovieClick = { favorite -> onMovieClick(favorite.id) }
                )
            }
        }
    }
}

enum class HomeTab {
    Popular, Favourite
}
