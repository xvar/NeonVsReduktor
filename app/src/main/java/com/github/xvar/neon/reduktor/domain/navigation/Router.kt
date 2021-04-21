package com.github.xvar.neon.reduktor.domain.navigation

import com.github.xvar.neon.reduktor.domain.navigation.action.Action

interface Router {
    fun handleAction(action: Action)
}