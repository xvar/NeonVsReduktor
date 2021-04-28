package com.github.xvar.neon.reduktor.domain.contract

interface Consumer<in T> {

    fun consume(event: T) {}

    object Empty: Consumer<Any>

}