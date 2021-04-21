package com.github.xvar.neon.reduktor.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.xvar.neon.reduktor.domain.navigation.Router
import com.github.xvar.neon.reduktor.domain.navigation.screen.HomeScreen
import com.github.xvar.neon.reduktor.ui.AppRouter
import com.github.xvar.neon.reduktor.ui.screen.Home
import com.github.xvar.neon.reduktor.ui.theme.NeonVsReduktorTheme

class LaunchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeonVsReduktorTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    val appRouter : Router = AppRouter(navController)
    Box(modifier = Modifier.fillMaxSize()) {
        val homeScreen = HomeScreen()
        NavHost(
            navController = navController,
            startDestination = homeScreen.destination,
        ) {
            composable(homeScreen.destination) {
                Home(appRouter)
            }
        }
    }
}