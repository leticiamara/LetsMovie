package com.leticiafernandes.letsmovie.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.leticiafernandes.letsmovie.R
import com.leticiafernandes.letsmovie.ui.favorite.FavoritesScreen
import com.leticiafernandes.letsmovie.ui.favorite.FavoritesViewModel
import com.leticiafernandes.letsmovie.ui.movie.MoviesScreen
import com.leticiafernandes.letsmovie.ui.movie.MoviesViewModel
import com.leticiafernandes.letsmovie.ui.theme.Primary
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Long) -> Unit
) {
    var currentTab by remember { mutableStateOf(HomeTab.Popular) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when (currentTab) {
                        HomeTab.Popular -> stringResource(R.string.title_populars)
                        HomeTab.Favourite -> stringResource(R.string.title_favourites)
                    }
                    Text(text = title, color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentTab == HomeTab.Popular,
                    onClick = { currentTab = HomeTab.Popular },
                    icon = { Icon(Icons.Default.Movie, contentDescription = null) },
                    label = { Text(stringResource(R.string.title_populars)) }
                )
                NavigationBarItem(
                    selected = currentTab == HomeTab.Favourite,
                    onClick = { currentTab = HomeTab.Favourite },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                    label = { Text(stringResource(R.string.title_favourites)) }
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
