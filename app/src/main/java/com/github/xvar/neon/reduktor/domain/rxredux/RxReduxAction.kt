package com.github.xvar.neon.reduktor.domain.rxredux

sealed class RxReduxAction {
    class Init(val data: RxReduxInitData) : RxReduxAction()
    class Back : RxReduxAction()
    class Increment : RxReduxAction()
    class Loading() : RxReduxAction()
    //todo add error sample
    class Error(val error: Throwable) : RxReduxAction()
    class Data(val counter: Int): RxReduxAction()
}