package com.github.xvar.neon.reduktor.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.github.xvar.neon.reduktor.domain.navigation.Router
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent
import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen
import com.github.xvar.neon.reduktor.ui.AppRouter
import com.github.xvar.neon.reduktor.ui.screen.home.HomeUI
import com.github.xvar.neon.reduktor.ui.screen.home.HomeVm
import com.github.xvar.neon.reduktor.ui.screen.neon.NeonUI
import com.github.xvar.neon.reduktor.ui.screen.reduktor.ReduktorUI
import com.github.xvar.neon.reduktor.ui.theme.NeonVsReduktorTheme
import com.github.xvar.neon.reduktor.ui.util.instanceDebug
import io.reactivex.Flowable

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
    //Проблема №1 - Навигация с инъекцией зависимостей
    //Вероятно, решается через attach/detach
    val navController = rememberNavController()
    val appRouter : Router = remember { AppRouter(navController) }.instanceDebug("main")

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = AppScreen.Home.destination,
        ) {
            composable(AppScreen.Home.destination) {
                //Особенности composable-функций - recompose может быть вызван любое кол-во раз
                val vm = viewModel<HomeVm>().instanceDebug("main")
                DisposableRouterEffect(router = appRouter, events = vm.routeEvents)
                HomeUI(vm)
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

@Composable
@NonRestartableComposable
private fun DisposableRouterEffect(
    router: Router,
    events: Flowable<RouteEvent>
) {
    DisposableEffect(router) {
        val disposable = events.subscribe {
            router.handle(it)
        }
        onDispose { disposable.dispose() }
    }
}