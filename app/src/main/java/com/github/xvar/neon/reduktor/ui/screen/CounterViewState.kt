package com.github.xvar.neon.reduktor.ui.screen

import com.github.xvar.neon.reduktor.domain.contract.ViewState

class CounterViewState(
    val counter: Int,
    val isLoading: Boolean = false,
    val someScreenDescription: String = ""
): ViewState