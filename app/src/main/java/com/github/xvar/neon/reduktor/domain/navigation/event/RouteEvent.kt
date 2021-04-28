package com.github.xvar.neon.reduktor.domain.navigation.event

import com.github.xvar.neon.reduktor.domain.navigation.screen.Screen

interface RouteEvent {
    val screen: Screen
    val route: String
}