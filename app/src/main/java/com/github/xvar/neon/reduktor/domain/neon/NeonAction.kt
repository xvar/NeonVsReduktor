package com.github.xvar.neon.reduktor.domain.neon

import com.sch.neon.Event

sealed class NeonAction : Event() {
    class Init(val data: NeonInitData) : NeonAction()
    class Back : NeonAction()
    class Increment : NeonAction()
    //todo add error sample
    class Error(val error: Throwable) : NeonAction()
    class Data(val counter: Int): NeonAction()
}