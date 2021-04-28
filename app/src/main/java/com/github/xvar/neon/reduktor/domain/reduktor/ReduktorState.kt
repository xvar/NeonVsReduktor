package com.github.xvar.neon.reduktor.domain.reduktor

data class ReduktorState(
    val isInitial: Boolean = false,
    val counter: Int = 0,
    val isLoading: Boolean = false,
    val title: String = ""
)