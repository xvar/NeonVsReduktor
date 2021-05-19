package com.github.xvar.neon.reduktor.domain.navigation.screen

import com.github.xvar.neon.reduktor.domain.navigation.asParam

//Could use separate classes
sealed class AppScreen : Screen {
    object Reduktor : AppScreen() {
        object Args {
            const val counter = "counter"
            const val title = "title"
            const val defaultTitle = "Reduktor"
        }
        override val destination: String = "reduktor/${Args.counter.asParam()}?${Args.title}=${Args.title.asParam()}"
    }

    object Back: AppScreen() {
        override val destination: String = "back"
    }

    object Home : AppScreen() {
        override val destination: String = "home"
    }

    object Neon : AppScreen() {
        override val destination: String = "counter/neon"
    }

    object RxRedux : AppScreen() {
        object Args {
            const val counter = "counter"
        }

        override val destination: String = "rxredux/${Args.counter.asParam()}"
    }
}