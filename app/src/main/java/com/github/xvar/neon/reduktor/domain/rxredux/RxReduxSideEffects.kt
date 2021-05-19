package com.github.xvar.neon.reduktor.domain.rxredux

import com.freeletics.rxredux.StateAccessor
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun loadSideEffect(actions : Observable<RxReduxAction>, state: StateAccessor<RxReduxState>) : Observable<RxReduxAction> {
    return actions
        .ofType(RxReduxAction.Increment::class.java)
        .switchMap {
            Observable.just(state())
                .map<RxReduxAction> { RxReduxAction.Data(counter = it.counter + 1) }
                .delay(750, TimeUnit.MILLISECONDS)
                .startWith(RxReduxAction.Loading())
        }
        .onErrorReturn { RxReduxAction.Error(it) }
}

fun backSideEffect(actions : Observable<RxReduxAction>, state: StateAccessor<RxReduxState>) : Observable<RxReduxAction> {
    return actions
        .ofType(RxReduxAction.Data::class.java)
        .switchMap {
            val shouldGoBack = state().counter == 20
            return@switchMap if (shouldGoBack)
                Observable.just(RxReduxAction.Back())
            else
                Observable.empty() //important (we won't like a cycle here)
        }
}