package com.leticiafernandes.letsmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.leticiafernandes.letsmovie.ui.navigation.Destination
import com.leticiafernandes.letsmovie.ui.navigation.NavGraph
import com.leticiafernandes.letsmovie.ui.theme.LetsMovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LetsMovieTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    startDestination = Destination.Splash.route
                )
            }
        }
    }
}
