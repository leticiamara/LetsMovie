package com.leticiafernandes.letsmovie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.leticiafernandes.letsmovie.infrastructure.SharedPreferencesManager
import com.leticiafernandes.letsmovie.ui.screens.LoginScreen
import com.leticiafernandes.letsmovie.ui.screens.SignUpScreen
import com.leticiafernandes.letsmovie.ui.screens.SplashScreen
import com.leticiafernandes.letsmovie.ui.home.HomeScreen
import com.leticiafernandes.movie.presentation.compose.MovieDetailScreen
import com.leticiafernandes.movie.presentation.detail.MovieDetailViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.coroutines.delay

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
            val context = LocalContext.current
            val sharedPreferencesManager = remember { SharedPreferencesManager(context) }
            
            LaunchedEffect(Unit) {
                delay(2000)
                if (sharedPreferencesManager.getUserEmail().isNotEmpty()) {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Splash.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(Destination.Login.route) {
                        popUpTo(Destination.Splash.route) { inclusive = true }
                    }
                }
            }
            SplashScreen()
        }
        composable(Destination.Login.route) {
            LoginScreen(
                onSignInClick = { _, _ ->
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Login.route) { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Destination.SignUp.route)
                }
            )
        }
        composable(Destination.SignUp.route) {
            SignUpScreen(
                onSignUpClick = { _, _ ->
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.SignUp.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Destination.Home.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Destination.MovieDetail.createRoute(movieId))
                }
            )
        }
        composable(
            route = Destination.MovieDetail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.LongType })
        ) {
            val viewModel: MovieDetailViewModel = hiltViewModel()
            MovieDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
