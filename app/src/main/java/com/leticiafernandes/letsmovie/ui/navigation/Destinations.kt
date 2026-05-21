package com.leticiafernandes.letsmovie.ui.navigation

sealed class Destination(val route: String) {
    object Splash : Destination("splash")
    object Home : Destination("home")
    object MovieDetail : Destination("movieDetail/{movieId}") {
        fun createRoute(movieId: Long) = "movieDetail/$movieId"
    }
}
