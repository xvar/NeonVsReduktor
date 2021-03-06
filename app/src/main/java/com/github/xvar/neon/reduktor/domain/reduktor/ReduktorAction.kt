package com.github.xvar.neon.reduktor.domain.reduktor

sealed class ReduktorAction {
    class Init(val data: ReduktorInitData) : ReduktorAction()
    class Back : ReduktorAction()
    class Increment : ReduktorAction()
    //todo add error sample
    class Error(val error: Throwable) : ReduktorAction()
    class StartLoading(): ReduktorAction()
    class Data(val counter: Int): ReduktorAction()
}