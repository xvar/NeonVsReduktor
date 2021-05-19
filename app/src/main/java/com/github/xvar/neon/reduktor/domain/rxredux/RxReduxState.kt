package com.github.xvar.neon.reduktor.domain.rxredux

data class RxReduxState(
    val isInitial: Boolean = false,
    val counter: Int = 0,
    val isLoading: Boolean = false,
    val title: String = "",
    val throwable: Throwable? = null
)