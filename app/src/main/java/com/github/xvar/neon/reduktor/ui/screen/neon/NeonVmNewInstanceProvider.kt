package com.github.xvar.neon.reduktor.ui.screen.neon

import com.github.xvar.neon.reduktor.domain.neon.*
import com.sch.neon.MainLoop
import io.reactivex.subjects.PublishSubject

class NeonVmNewInstanceProvider() {

    fun get() : NeonVm {
        val reducer = NeonStateReducer()
        val effectHandler = NeonEffectHandler()
        val externalEvents = PublishSubject.create<NeonAction>()
        val listener = NeonListener(NeonMapper())
        val mainLoop = MainLoop<NeonAction, NeonState, NeonEffect>(
            reducer = reducer,
            effectHandler = effectHandler,
            externalEvents = externalEvents,
            listener = listener
        )
        return NeonVm(mainLoop, externalEvents, listener)
    }

}