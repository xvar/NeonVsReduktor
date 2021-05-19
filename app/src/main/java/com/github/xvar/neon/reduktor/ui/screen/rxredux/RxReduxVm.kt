package com.github.xvar.neon.reduktor.ui.screen.rxredux

import android.util.Log
import com.freeletics.rxredux.Reducer
import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.StateAccessor
import com.freeletics.rxredux.reduxStore
import com.github.xvar.neon.reduktor.domain.contract.UIBack
import com.github.xvar.neon.reduktor.domain.contract.UIEvent
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteBack
import com.github.xvar.neon.reduktor.domain.rxredux.RxReduxAction
import com.github.xvar.neon.reduktor.domain.rxredux.RxReduxState
import com.github.xvar.neon.reduktor.ui.BaseVm
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RxReduxVm(
    //Можно не передавать извне, а создать внутри ViewModel
    //Доступны оба варианта
    initialState: RxReduxState,
    sideEffects: List<SideEffect<RxReduxState, RxReduxAction>>,
    reducer: Reducer<RxReduxState, RxReduxAction>
) : BaseVm<CounterViewState>() {

    private val compositeDisposable = CompositeDisposable()
    private val uiConsumer = PublishSubject.create<UIEvent>()

    private fun routeSideEffect(actions : Observable<RxReduxAction>, state: StateAccessor<RxReduxState>) : Observable<RxReduxAction> {
        return actions.ofType(RxReduxAction.Back::class.java)
            .switchMap {
                Observable.just(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { postRouteEvent(RouteBack) }
            }
    }

    init {
        val stateObservable = uiConsumer
            .map {
                return@map when(it) {
                    is UIBack -> RxReduxAction.Back()
                    is RxReduxUiEvent.AddCounter -> RxReduxAction.Increment()
                    else -> throw IllegalStateException("unsupported ui event")
                }
            }
            .reduxStore(
                initialState = initialState,
                //добавили side-effect для роутинга "на лету"
                sideEffects = (sideEffects + ::routeSideEffect),
                reducer = reducer
            )
            .distinctUntilChanged()

        compositeDisposable.add(
            stateObservable.map {
                //state to viewState map
                CounterViewState(
                    counter = it.counter,
                    isLoading = it.isLoading,
                    someScreenDescription = it.title
                )
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    //Может, можно подписать ViewStateProcessor напрямую
                    { postViewState(it) },
                    { Log.e("rx redux error", "error", it) }
                )
        )
    }

    override fun consume(event: UIEvent) {
        super.consume(event)
        uiConsumer.onNext(event)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    sealed class RxReduxUiEvent : UIEvent {
        class AddCounter : RxReduxUiEvent()
    }

}