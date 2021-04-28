package com.github.xvar.neon.reduktor.ui

import androidx.lifecycle.ViewModel
import com.github.xvar.neon.reduktor.domain.contract.Consumer
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent
import com.github.xvar.neon.reduktor.domain.contract.UIEvent
import com.github.xvar.neon.reduktor.domain.contract.ViewState
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor

abstract class BaseVm<T: ViewState>: ViewModel(), Consumer<UIEvent> {

    private val viewStateProcessor: BehaviorProcessor<T>
            by lazy(mode = LazyThreadSafetyMode.NONE) { BehaviorProcessor.create<T>() }

    val viewStates: Flowable<T>
            by lazy(mode = LazyThreadSafetyMode.NONE) { viewStateProcessor.toSerialized() }

    private val routeEventProcessor: PublishProcessor<RouteEvent>
            by lazy(mode = LazyThreadSafetyMode.NONE) { PublishProcessor.create() }

    val routeEvents: Flowable<RouteEvent>
            by lazy(mode = LazyThreadSafetyMode.NONE) { routeEventProcessor.toSerialized() }

    protected fun postViewState(vs: T): Unit = viewStateProcessor.onNext(vs)

    protected fun postRouteEvent(re: RouteEvent) = routeEventProcessor.onNext(re)

}