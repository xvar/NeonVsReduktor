package com.github.xvar.neon.reduktor.ui.screen.reduktor

import com.github.xvar.neon.reduktor.domain.contract.ServiceEvent
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteBack
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent
import com.github.xvar.neon.reduktor.domain.reduktor.ReduktorAction
import com.github.xvar.neon.reduktor.domain.reduktor.ReduktorState
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import ru.g000sha256.reduktor.Mapper

class ReduktorMapper : Mapper<ReduktorAction, ReduktorState, RouteEvent, ServiceEvent, CounterViewState> {

    override fun actionToRouteEvent(action: ReduktorAction): RouteEvent? {
        //base back
        return if (action is ReduktorAction.Back) RouteBack else null
    }

    override fun actionToViewEvent(action: ReduktorAction): ServiceEvent? {
        //no side-effects for today
        return null
    }

    override fun stateToViewState(state: ReduktorState): CounterViewState {
        return when {
            state.isInitial -> CounterViewState(
                counter = 0,
                isLoading = false,
                someScreenDescription = "Just Started"
            )
            else -> CounterViewState(
                counter = state.counter,
                isLoading = state.isLoading,
                someScreenDescription = state.title
            )
        }
    }

}