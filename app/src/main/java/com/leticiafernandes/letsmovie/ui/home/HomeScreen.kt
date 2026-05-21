package com.leticiafernandes.letsmovie.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.movie.presentation.MoviesViewModel
import com.leticiafernandes.movie.presentation.compose.MoviesScreen

@Composable
fun HomeScreen(
    onMovieClick: (Long) -> Unit
) {
    var currentTab by remember { mutableStateOf(HomeTab.Popular) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentTab == HomeTab.Popular,
                    onClick = { currentTab = HomeTab.Popular },
                    icon = { Icon(Icons.Default.Movie, contentDescription = null) },
                    label = { Text(stringResource(id = R.string.title_populars)) }
                )
                NavigationBarItem(
                    selected = currentTab == HomeTab.Favourite,
                    onClick = { currentTab = HomeTab.Favourite },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                    label = { Text(stringResource(id = R.string.title_favourites)) }
                )
            }
        }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        when (currentTab) {
            HomeTab.Popular -> {
                val viewModel: MoviesViewModel = hiltViewModel()
                MoviesScreen(
                    viewModel = viewModel,
                    modifier = modifier,
                    onMovieClick = { movie -> onMovieClick(movie.id) }
                )
            }
            HomeTab.Favourite -> {
                FavouriteMoviesScreen(
                    modifier = modifier,
                    onMovieClick = { movie -> onMovieClick(movie.id) }
                )
            }
        }
    }
}

enum class HomeTab {
    Popular, Favourite
}
