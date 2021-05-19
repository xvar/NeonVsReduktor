package com.github.xvar.neon.reduktor.domain.navigation.event

import com.github.xvar.neon.reduktor.domain.navigation.asParam
import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen
import com.github.xvar.neon.reduktor.domain.navigation.screen.Screen

class RxReduxRouteEvent(
    private val startCounter: Int
) : RouteEvent {
    override val screen: Screen = AppScreen.RxRedux
    override val route: String
        get() = screen.destination
            .replace(AppScreen.RxRedux.Args.counter.asParam(), startCounter.toString())
}