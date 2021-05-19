package com.github.xvar.neon.reduktor.ui.screen.rxredux

import com.github.xvar.neon.reduktor.domain.rxredux.RxReduxReducer
import com.github.xvar.neon.reduktor.domain.rxredux.RxReduxState
import com.github.xvar.neon.reduktor.domain.rxredux.backSideEffect
import com.github.xvar.neon.reduktor.domain.rxredux.loadSideEffect

class RxReduxVmNewInstanceProvider {

    fun get(initCounter: Int) : RxReduxVm {
        val initState = RxReduxState(
            isInitial = true,
            counter = initCounter,
        )
        return RxReduxVm(
            initialState = initState,
            sideEffects = listOf(::loadSideEffect, ::backSideEffect),
            reducer = RxReduxReducer()
        )
    }

}