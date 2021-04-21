package com.github.xvar.neon.reduktor.domain.navigation.action

import com.github.xvar.neon.reduktor.domain.navigation.screen.Screen

interface Action {
    val screen: Screen
    val route: String
}