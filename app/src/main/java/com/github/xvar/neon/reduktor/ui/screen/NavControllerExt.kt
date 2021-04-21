package com.github.xvar.neon.reduktor.ui.screen

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.navigate
import com.github.xvar.neon.reduktor.domain.navigation.action.Action

fun NavController.navigate(action: Action, builder: NavOptionsBuilder.() -> Unit = {}) =
    navigate(action.route, builder)