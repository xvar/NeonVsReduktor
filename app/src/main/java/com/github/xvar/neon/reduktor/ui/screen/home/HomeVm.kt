package com.github.xvar.neon.reduktor.ui.screen.home

import com.github.xvar.neon.reduktor.domain.navigation.Router
import com.github.xvar.neon.reduktor.domain.navigation.action.NeonAction
import com.github.xvar.neon.reduktor.domain.navigation.action.ReduktorAction
import com.github.xvar.neon.reduktor.ui.BaseVm
import com.github.xvar.neon.reduktor.ui.contract.UIEvent
import com.github.xvar.neon.reduktor.ui.contract.ViewState

class HomeVm(
    private val router: Router
) : BaseVm<ViewState>() {

    fun consume(event: UIEvent) {
        when(event) {
            is Click.NeonButton -> router.handleAction(NeonAction())
            is Click.ReduktorButton -> router.handleAction(ReduktorAction(
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