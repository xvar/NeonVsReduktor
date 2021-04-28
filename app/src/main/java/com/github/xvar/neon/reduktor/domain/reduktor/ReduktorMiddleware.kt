package com.github.xvar.neon.reduktor.domain.reduktor

import io.reactivex.rxjava3.core.Observable
import ru.g000sha256.reduktor.Middleware
import ru.g000sha256.schedulers.SchedulersHolder
import java.util.concurrent.TimeUnit

class ReduktorMiddleware(
    //не понадобилось, предполагаю, что в проде может
    private val schedulersHolder: SchedulersHolder
) : Middleware<ReduktorAction, ReduktorState>{

    override fun afterReduce(
        actionObservable: Observable<ReduktorAction>,
        stateAccessor: () -> ReduktorState
    ): Observable<ReduktorAction> {
        return Observable.empty()
    }

    override fun beforeReduce(
        actionObservable: Observable<ReduktorAction>,
        stateAccessor: () -> ReduktorState
    ): Observable<ReduktorAction> {
        return actionObservable.flatMap {
            when(it) {
                is ReduktorAction.StartLoading -> {
                    val state = stateAccessor()
                    return@flatMap when {
                        state.isLoading -> Observable.empty<ReduktorAction>()
                        else -> loadDataObservable(stateAccessor)
                            .map<ReduktorAction> { data -> ReduktorAction.Data(data) }
                            .onErrorReturn { error -> ReduktorAction.Error(error) }
                    }
                }
                is ReduktorAction.Increment ->
                    return@flatMap Observable.just(ReduktorAction.StartLoading())
                //some weird "business" logic (go back on 50-th turn)
                is ReduktorAction.Data -> return@flatMap if (it.counter % 20 == 0)
                    Observable.just(ReduktorAction.Back())
                else
                    Observable.empty()
                else -> Observable.empty<ReduktorAction>()
            }
        }
    }

    private fun loadDataObservable(stateAccessor: () -> ReduktorState) : Observable<Int> {
        return Observable.fromCallable { stateAccessor().counter + 1 }
            .delay(750, TimeUnit.MILLISECONDS)
    }

}