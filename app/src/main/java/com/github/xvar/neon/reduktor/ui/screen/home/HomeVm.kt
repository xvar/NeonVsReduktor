package com.github.xvar.neon.reduktor.ui.screen.home

import com.github.xvar.neon.reduktor.domain.navigation.event.NeonRouteEvent
import com.github.xvar.neon.reduktor.domain.navigation.event.ReduktorRouteEvent
import com.github.xvar.neon.reduktor.ui.BaseVm
import com.github.xvar.neon.reduktor.ui.contract.UIEvent
import com.github.xvar.neon.reduktor.ui.contract.ViewState

class HomeVm() : BaseVm<ViewState>() {

    override fun consume(event: UIEvent) {
        when(event) {
            is Click.NeonButton -> postRouteEvent(NeonRouteEvent())
            is Click.ReduktorButton -> postRouteEvent(ReduktorRouteEvent(
                counter = (System.currentTimeMillis() % 100).toInt(),
                title = event.title
            ))
        }
    }

    sealed class Click : UIEvent {
        class NeonButton(val title: String): Click()
        class ReduktorButton(val title: String) : Click()
    }
}