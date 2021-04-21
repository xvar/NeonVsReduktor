package com.github.xvar.neon.reduktor.domain.navigation.action

import com.github.xvar.neon.reduktor.domain.navigation.screen.NeonScreen

class NeonAction : Action {
    override val screen = NeonScreen()
    override val route: String = screen.destination
}