package com.github.xvar.neon.reduktor.ui.screen.reduktor

import android.content.Context
import com.github.xvar.neon.reduktor.BuildConfig
import com.github.xvar.neon.reduktor.domain.contract.ServiceEvent
import com.github.xvar.neon.reduktor.domain.navigation.event.RouteEvent
import com.github.xvar.neon.reduktor.domain.reduktor.*
import com.github.xvar.neon.reduktor.ui.App
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import ru.g000sha256.reduktor.Store

class ReduktorVmNewInstanceProvider(
    private val ctx: Context
) {

    fun get(initData: ReduktorInitData? = null): ReduktorVm {
        val app = ctx.applicationContext as App
        val mapper = ReduktorMapper()
        val schedulersHolderRx3 = app.schedulersHolderRx3
        val middleware = ReduktorMiddleware(schedulersHolderRx3)
        val reducer = ReduktorReducer()
        val scheduler = schedulersHolderRx3.ioScheduler
        val store =
            Store<ReduktorAction, ReduktorState, RouteEvent, ServiceEvent, CounterViewState>(
                enableLogs = BuildConfig.DEBUG,
                mapper = mapper,
                middleware = middleware,
                reducer = reducer,
                scheduler = scheduler,
                state = initData?.let {
                    ReduktorState(
                        title = it.initText,
                        counter = it.initCounter
                    )
                } ?: ReduktorState(isInitial = true)
            )
        return ReduktorVm(
            store = store,
            schedulersHolder = schedulersHolderRx3
        )
    }

}