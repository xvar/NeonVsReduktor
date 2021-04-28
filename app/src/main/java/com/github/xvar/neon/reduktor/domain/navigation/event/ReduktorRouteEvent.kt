package com.github.xvar.neon.reduktor.domain.navigation.event

import com.github.xvar.neon.reduktor.domain.navigation.asParam
import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen

class ReduktorRouteEvent(
    private val counter: Int,
    private val title: String?
): RouteEvent {
    override val screen = AppScreen.Reduktor
    override val route: String
        get() = screen.destination
            .replace(AppScreen.Reduktor.Args.counter.asParam(), counter.toString())
            .replace(AppScreen.Reduktor.Args.title.asParam(), title ?: AppScreen.Reduktor.Args.defaultTitle)
}