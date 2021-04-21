package com.github.xvar.neon.reduktor.domain.navigation.action

import com.github.xvar.neon.reduktor.domain.navigation.screen.ReduktorScreen
import com.github.xvar.neon.reduktor.domain.navigation.asParam

class ReduktorAction(
    private val initCounter: Int,
    private val title: String?
): Action {
    override val screen = ReduktorScreen()
    override val route: String
        get() = screen.destination
            .replace(screen.counter.asParam(), initCounter.toString())
            .replace(screen.title.asParam(), title ?: screen.defaultTitle)
}