package com.github.xvar.neon.reduktor.ui.screen

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.navigate
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent

fun NavController.navigate(routeEvent: RouteEvent, builder: NavOptionsBuilder.() -> Unit = {}) =
    navigate(routeEvent.route, builder)