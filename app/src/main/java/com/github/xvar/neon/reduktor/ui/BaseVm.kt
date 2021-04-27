package com.github.xvar.neon.reduktor.ui

import androidx.lifecycle.ViewModel
import com.github.xvar.neon.reduktor.ui.contract.ViewState
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

abstract class BaseVm<T: ViewState>: ViewModel() {

    private val viewStateProcessor: BehaviorProcessor<T>
            by lazy(mode = LazyThreadSafetyMode.NONE) { BehaviorProcessor.create<T>() }

    val viewState: Flowable<T>
            by lazy(mode = LazyThreadSafetyMode.NONE) { viewStateProcessor.toSerialized() }

    protected fun postViewState(vs: T): Unit = viewStateProcessor.onNext(vs)

}