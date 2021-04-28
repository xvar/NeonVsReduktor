package com.github.xvar.neon.reduktor.domain.navigation.event

import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen
import com.github.xvar.neon.reduktor.domain.navigation.screen.Screen

interface RouteEvent {
    val screen: Screen
    /**
     * Fully assembled screen path, e.g. /user/31337
     */
    val route: String
}

/**
 * Fake object for driving back via code events
 */
object RouteBack : RouteEvent {
    override val screen: Screen = AppScreen.Back
    override val route: String = screen.destination
}