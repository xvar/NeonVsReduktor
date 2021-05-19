package com.github.xvar.neon.reduktor.ui.screen.home

import com.github.xvar.neon.reduktor.domain.navigation.event.NeonRouteEvent
import com.github.xvar.neon.reduktor.domain.navigation.event.ReduktorRouteEvent
import com.github.xvar.neon.reduktor.ui.BaseVm
import com.github.xvar.neon.reduktor.domain.contract.UIEvent
import com.github.xvar.neon.reduktor.domain.contract.ViewState
import com.github.xvar.neon.reduktor.domain.navigation.event.RxReduxRouteEvent

class HomeVm() : BaseVm<ViewState>() {

    override fun consume(event: UIEvent) {
        when(event) {
            is Click.NeonButton -> postRouteEvent(NeonRouteEvent())
            is Click.ReduktorButton -> postRouteEvent(ReduktorRouteEvent(
                counter = (System.currentTimeMillis() % 20).toInt(),
                title = event.title
            ))
            is Click.RxRedux -> postRouteEvent(RxReduxRouteEvent(
                startCounter = (System.currentTimeMillis() % 20).toInt()
            ))
        }
    }

    sealed class Click : UIEvent {
        class NeonButton(val title: String): Click()
        class ReduktorButton(val title: String) : Click()
        class RxRedux: Click()
    }
}