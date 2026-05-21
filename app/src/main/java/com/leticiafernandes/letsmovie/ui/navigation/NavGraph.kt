package com.leticiafernandes.letsmovie.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.leticiafernandes.letsmovie.ui.home.HomeScreen
import com.leticiafernandes.letsmovie.ui.login.SplashScreen
import com.leticiafernandes.letsmovie.ui.login.SplashViewModel
import com.leticiafernandes.letsmovie.ui.movie.MovieDetailScreen
import com.leticiafernandes.letsmovie.ui.movie.MovieDetailViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Splash.route) {
            val viewModel: SplashViewModel = hiltViewModel()
            val ready by viewModel.ready.collectAsState()

            LaunchedEffect(ready) {
                if (ready) {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Splash.route) { inclusive = true }
                    }
                }
            }
            SplashScreen()
        }

        composable(
            route = Destination.Home.route,
            exitTransition = { slideOutHorizontally { -it / 3 } + fadeOut() },
            popEnterTransition = { slideInHorizontally { -it / 3 } + fadeIn() }
        ) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Destination.MovieDetail.createRoute(movieId))
                }
            )
        }

        composable(
            route = Destination.MovieDetail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.LongType }),
            enterTransition = { slideInHorizontally { it } + fadeIn() },
            popExitTransition = { slideOutHorizontally { it } + fadeOut() }
        ) {
            val viewModel: MovieDetailViewModel = hiltViewModel()
            MovieDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
