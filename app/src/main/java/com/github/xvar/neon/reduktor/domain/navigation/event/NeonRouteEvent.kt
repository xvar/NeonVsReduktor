package com.github.xvar.neon.reduktor.domain.navigation.event

import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen

class NeonRouteEvent : RouteEvent {
    override val screen = AppScreen.Neon
    override val route: String = screen.destination
}