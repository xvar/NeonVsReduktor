package com.github.xvar.neon.reduktor.ui.screen.neon

import com.github.xvar.neon.reduktor.domain.contract.ServiceEvent
import com.github.xvar.neon.reduktor.domain.contract.ViewState
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteBack
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent
import com.github.xvar.neon.reduktor.domain.neon.NeonAction
import com.github.xvar.neon.reduktor.domain.neon.NeonEffect
import com.github.xvar.neon.reduktor.domain.neon.NeonState
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState

interface ViewStateMapper<U, T: ViewState> {
    fun mapViewState(state: U) : T
}

//создаю один класс, однако можно разбить на разные классы
class NeonMapper: ViewStateMapper<NeonState, CounterViewState> {

    override fun mapViewState(state: NeonState): CounterViewState {
        return when {
            state.isInitial -> CounterViewState(
                counter = 0,
                isLoading = false,
                someScreenDescription = "Just Started"
            )
            else -> CounterViewState(
                counter = state.counter,
                isLoading = state.isLoading,
                someScreenDescription = "Incremented counter, total: ${state.counter}"
            )
        }
    }

    fun mapEffectToServiceEvent(effect: NeonEffect) : ServiceEvent? {
        return null
    }

    fun mapActionToServiceEvent(action: NeonAction) : ServiceEvent? {
        return null
    }

    fun mapActionToRouteEvent(action: NeonAction) : RouteEvent? {
        return when {
            action is NeonAction.Data && action.counter == 20 -> RouteBack
            action is NeonAction.Back -> RouteBack
            else -> null
        }
    }

}