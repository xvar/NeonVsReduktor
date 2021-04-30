package com.github.xvar.neon.reduktor.ui.screen.neon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import com.github.xvar.neon.reduktor.ui.screen.common.CounterScreen
import com.github.xvar.neon.reduktor.ui.util.instanceDebug

//Дефолтный конструктор не может быть использован для корректной работы
//Он нужен только для удобства построения preview
@Composable
fun NeonUI(vm: NeonVm = viewModel()) {
    vm.instanceDebug("neon")
    CounterScreen(
        vm.viewStates.subscribeAsState(initial = CounterViewState(0)),
        onCounterClick = { vm.consume(NeonVm.NeonUIEvent.AddCounter()) }
    )
}