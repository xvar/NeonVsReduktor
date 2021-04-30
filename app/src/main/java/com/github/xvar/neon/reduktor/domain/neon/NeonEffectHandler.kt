package com.github.xvar.neon.reduktor.domain.neon

import com.sch.neon.EffectHandler
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

//Effects и Actions могут быть преобразованы друг в друга
//В общем, концепция похожа на RxRedux от Hannes Dorfman
//[https://github.com/freeletics/RxRedux]
//
//Effect следует воспринимать как UseCase для чистой архитектуры (например, загрузка страницы)
//В RxRedux side-effect можно определить отдельно, в этой реализации - всё вместе в рамках одного
//effect handler
class NeonEffectHandler : EffectHandler<NeonEffect, NeonAction> {

    override fun handle(effects: Observable<NeonEffect>): Observable<NeonAction> {
        return effects.ofType(
            NeonEffect.LoadNextPage::class.java
        ).switchMap {
            loadPage(it.currentCounter)
                .onErrorReturn { error -> NeonAction.Error(error) }
        }
    }

    //could be injected
    private fun loadPage(counter: Int) : Observable<NeonAction> {
        return Observable.just(counter + 1)
            .map<NeonAction> { NeonAction.Data(it) }
            .delay(750, TimeUnit.MILLISECONDS)
    }

}