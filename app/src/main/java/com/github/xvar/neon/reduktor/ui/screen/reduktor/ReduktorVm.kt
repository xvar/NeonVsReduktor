package com.github.xvar.neon.reduktor.ui.screen.reduktor

import com.github.xvar.neon.reduktor.domain.contract.ServiceEvent
import com.github.xvar.neon.reduktor.domain.contract.UIBack
import com.github.xvar.neon.reduktor.domain.contract.UIEvent
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent
import com.github.xvar.neon.reduktor.domain.reduktor.ReduktorAction
import com.github.xvar.neon.reduktor.domain.reduktor.ReduktorState
import com.github.xvar.neon.reduktor.ui.BaseVm
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.g000sha256.reduktor.Store
import ru.g000sha256.schedulers.SchedulersHolder

//Разделил UIEvents и ReduktorActions, т.к. ReduktorActions, уходя из ViewModel "зацикливаются" в
//Middleware (т.е. могут быть использованы и самой Middleware), а также
// используются Reducer для формирования state.
// В последнем случае Actions направлены во ViewModel.
class ReduktorVm(
    private val store: Store<ReduktorAction, ReduktorState, RouteEvent, ServiceEvent, CounterViewState>,
    private val schedulersHolder: SchedulersHolder
) : BaseVm<CounterViewState>() {

    private val compositeDisposable = CompositeDisposable()
    init {
        compositeDisposable.add(
            store.subscribe()
        )

        //Версии RxJava отличаются, поэтому подписка так
        //В продакшн можно доработать. :)
        compositeDisposable.add(
            store.routeEventObservable
                .observeOn(schedulersHolder.mainImmediateScheduler)
                .subscribe { postRouteEvent(it) }
        )
        compositeDisposable.add(
            store.viewStateObservable
                .observeOn(schedulersHolder.mainImmediateScheduler)
                .subscribe { postViewState(it) }
        )
    }

    //RedUIEvent.Init не понадобился, т.к. начальное состояние задано на момент создания store
    //В production может быть по-другому.
    override fun consume(event: UIEvent) {
        when (event) {
            is UIBack -> store.actionConsumer.invoke(ReduktorAction.Back())
            /*is RedUIEvent.Init -> store.actionConsumer.invoke(
                ReduktorAction.Init(event.data)
            )*/
            is RedUIEvent.AddCounter -> store.actionConsumer.invoke(ReduktorAction.Increment())
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    sealed class RedUIEvent: UIEvent {
        /*class Init(val data: InitData): RedUIEvent()*/
        class AddCounter: RedUIEvent()
    }

}