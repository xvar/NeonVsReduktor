package com.github.xvar.neon.reduktor.ui

import android.app.Application
import ru.g000sha256.scheduler.MainSchedulerFactoryImpl
import ru.g000sha256.schedulers.Schedulers
import ru.g000sha256.schedulers.SchedulersFactory
import ru.g000sha256.schedulers.SchedulersHolder
import ru.g000sha256.schedulers.SchedulersImpl

class App : Application() {

    val schedulersFactoryRx3 by lazy<SchedulersFactory> { schedulersRx3 }
    val schedulersHolderRx3 by lazy<SchedulersHolder> { schedulersRx3 }
    private val schedulersRx3 by lazy { initSchedulersRx3() }

    private fun initSchedulersRx3(): Schedulers {
        val mainSchedulerFactory = MainSchedulerFactoryImpl()
        return SchedulersImpl(mainSchedulerFactory)
    }

    override fun onCreate() {
        super.onCreate()
    }

}