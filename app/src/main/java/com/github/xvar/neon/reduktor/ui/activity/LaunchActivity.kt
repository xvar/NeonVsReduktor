package com.github.xvar.neon.reduktor.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.github.xvar.neon.reduktor.domain.navigation.Router
import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen
import com.github.xvar.neon.reduktor.ui.AppRouter
import com.github.xvar.neon.reduktor.ui.screen.home.HomeUI
import com.github.xvar.neon.reduktor.ui.screen.home.HomeVm
import com.github.xvar.neon.reduktor.ui.screen.neon.NeonUI
import com.github.xvar.neon.reduktor.ui.screen.reduktor.ReduktorUI
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
    val appRouter : Router = remember { AppRouter(navController) }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = AppScreen.Home.destination,
        ) {
            composable(AppScreen.Home.destination) {
                HomeUI(HomeVm(appRouter).also { Log.e(
                    "compose_debug", "vm = $it"
                ) })
            }
            composable(AppScreen.Neon.destination) {
                NeonUI()
            }
            composable(
                route = AppScreen.Reduktor.destination,
                arguments = listOf(
                    navArgument(AppScreen.Reduktor.Args.counter) { type = NavType.IntType },
                    navArgument(AppScreen.Reduktor.Args.title) { defaultValue = AppScreen.Reduktor.Args.defaultTitle }
                )
            ) {
                ReduktorUI()
            }
        }
    }
}