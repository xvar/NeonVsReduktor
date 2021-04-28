package com.github.xvar.neon.reduktor.ui.contract

interface Consumer<in T> {

    fun consume(event: T) {}

    object Empty: Consumer<Any>

}