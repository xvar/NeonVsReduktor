package com.github.xvar.neon.reduktor.ui

import androidx.navigation.NavController
import androidx.navigation.compose.popUpTo
import com.github.xvar.neon.reduktor.domain.navigation.Router
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent
import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen
import com.github.xvar.neon.reduktor.ui.screen.navigate

class AppRouter(private val appNavController: NavController): Router {

    override fun handle(routeEvent: RouteEvent) {
        when(routeEvent.screen) {
            is AppScreen.Home -> appNavController.navigate(routeEvent) {
                launchSingleTop = true
            }
            else -> appNavController.navigate(routeEvent) {
                popUpTo(route = AppScreen.Home.destination) {}
            }
        }
    }

}