package com.github.xvar.neon.reduktor.ui.screen.neon

import com.github.xvar.neon.reduktor.domain.contract.UIBack
import com.github.xvar.neon.reduktor.domain.contract.UIEvent
import com.github.xvar.neon.reduktor.domain.neon.NeonAction
import com.github.xvar.neon.reduktor.domain.neon.NeonEffect
import com.github.xvar.neon.reduktor.domain.neon.NeonListener
import com.github.xvar.neon.reduktor.domain.neon.NeonState
import com.github.xvar.neon.reduktor.ui.BaseVm
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import com.sch.neon.MainLoop
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class NeonVm(
    private val mainLoop: MainLoop<NeonAction, NeonState, NeonEffect>,
    private val externalEvents: PublishSubject<NeonAction>,
    private val listener: NeonListener
) : BaseVm<CounterViewState>() {

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(
            listener.routeEvents
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { postRouteEvent(it) }
        )

        compositeDisposable.add(
            listener.viewStates
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { postViewState(it) }
        )

        compositeDisposable.add(
            mainLoop.loop(initialState = NeonState(counter = 0))
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    //UIEvent.Init нет, т.к. поленился передать данные в экран :)
    override fun consume(event: UIEvent) {
        when(event) {
            //correct way
            is UIBack -> mainLoop.dispatch(NeonAction.Back())
            //incorrect (though possible) :)
            is NeonUIEvent.AddCounter -> externalEvents.onNext(NeonAction.Increment())
        }
        super.consume(event)
    }

    sealed class NeonUIEvent: UIEvent {
        class AddCounter: NeonUIEvent()
    }

}