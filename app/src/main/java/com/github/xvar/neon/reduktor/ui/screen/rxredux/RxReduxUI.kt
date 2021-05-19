package com.github.xvar.neon.reduktor.ui.screen.rxredux

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import com.github.xvar.neon.reduktor.ui.screen.common.CounterScreen

//Дефолтный конструктор не может быть использован для корректной работы
//Он нужен только для удобства построения preview
@Composable
fun RxReduxUI(vm : RxReduxVm = viewModel()) {
    CounterScreen(
        vm.viewStates.subscribeAsState(initial = CounterViewState(0)),
        onCounterClick = { vm.consume(RxReduxVm.RxReduxUiEvent.AddCounter()) }
    )
}