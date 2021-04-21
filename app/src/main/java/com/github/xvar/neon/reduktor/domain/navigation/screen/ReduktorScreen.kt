package com.github.xvar.neon.reduktor.domain.navigation.screen

import com.github.xvar.neon.reduktor.domain.navigation.asParam

//or object - depending on Screen quantity
class ReduktorScreen : Screen {
    val counter = "counter"
    val title = "title"
    val defaultTitle = "Reduktor"
    override val destination: String = "reduktor/${counter.asParam()}?$title=${title.asParam()}"
}