package com.github.xvar.neon.reduktor.domain.navigation

import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent

interface Router {

    fun handle(routeEvent: RouteEvent)

}