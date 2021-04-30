package com.github.xvar.neon.reduktor.domain.neon

import com.github.xvar.neon.reduktor.domain.contract.ServiceEvent
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import com.github.xvar.neon.reduktor.ui.screen.neon.NeonMapper
import com.github.xvar.neon.reduktor.ui.util.instanceDebug
import com.sch.neon.MainLoop
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

//Пожалуй, с учётом того, что фреймворк реактивный - listener довольно странный способ для
//организации реакции на события.
//Предполагаю, что поток для навигации придётся определять здесь.

//Listener для production следует сделать параметризированным, подобно NeonMapper
class NeonListener(
    private val mapper: NeonMapper
) : MainLoop.Listener<NeonAction, NeonState, NeonEffect> {

    /**
     * IRL важно не забыть переключить потоки (возможно, здесь или как договоримся).
     * В данном примере - переключение в NeonVm
     * пояснение ниже
     */
    private val _routeEvents = PublishProcessor.create<RouteEvent>()
    val routeEvents: Flowable<RouteEvent> = _routeEvents

    private val _serviceEvents = PublishProcessor.create<ServiceEvent>()
    val serviceEvents: Flowable<ServiceEvent> = _serviceEvents

    private val _viewStates = PublishProcessor.create<CounterViewState>()
    val viewStates: Flowable<CounterViewState> = _viewStates

    //судя по коду либы, методы ниже будут выполняться на треде из Schedulers.computation()
    override fun onEffect(effect: NeonEffect) {
        effect.instanceDebug("onEffect")
        mapper.mapEffectToServiceEvent(effect)?.let {
            _serviceEvents.onNext(it)
        }
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun onEvent(action: NeonAction) {
        action.instanceDebug("onEvent")
        mapper.mapActionToRouteEvent(action)?.let {
            _routeEvents.onNext(it)
        }
        mapper.mapActionToServiceEvent(action)?.let {
            _serviceEvents.onNext(it)
        }
    }

    override fun onState(state: NeonState) {
        state.instanceDebug("onState")
        val vs = mapper.mapViewState(state)
        _viewStates.onNext(vs)
    }

}