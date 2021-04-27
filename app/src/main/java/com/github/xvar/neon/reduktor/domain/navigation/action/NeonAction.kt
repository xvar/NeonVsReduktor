package com.github.xvar.neon.reduktor.domain.navigation.action

import com.github.xvar.neon.reduktor.domain.navigation.screen.AppScreen

class NeonAction : Action {
    override val screen = AppScreen.Neon
    override val route: String = screen.destination
}