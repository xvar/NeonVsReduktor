package com.github.xvar.neon.reduktor.domain.neon

data class NeonState(
    val counter: Int = 0,
    val throwable: Throwable? = null,
    val isLoading: Boolean = false,
    val isInitial: Boolean = false
)