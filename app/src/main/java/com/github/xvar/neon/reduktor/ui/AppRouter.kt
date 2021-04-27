package com.github.xvar.neon.reduktor.ui

import androidx.navigation.NavController
import androidx.navigation.compose.popUpTo
import com.github.xvar.neon.reduktor.domain.navigation.Router
import com.github.xvar.neon.reduktor.domain.navigation.action.Action
import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen
import com.github.xvar.neon.reduktor.ui.screen.navigate

class AppRouter(private val appNavController: NavController): Router {

    override fun handleAction(action: Action) {
        when(action.screen) {
            is AppScreen.Home -> appNavController.navigate(action) {
                launchSingleTop = true
            }
            else -> appNavController.navigate(action) {
                popUpTo(route = AppScreen.Home.destination) {}
            }
        }
    }

}