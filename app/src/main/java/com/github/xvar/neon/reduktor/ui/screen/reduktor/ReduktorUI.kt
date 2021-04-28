package com.github.xvar.neon.reduktor.ui.screen.reduktor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.xvar.neon.reduktor.ui.screen.CounterViewState
import com.github.xvar.neon.reduktor.ui.screen.common.CounterScreen
import com.github.xvar.neon.reduktor.ui.util.instanceDebug

@Composable
fun ReduktorUI(vm: ReduktorVm = viewModel()) {
    vm.instanceDebug("reduktor")
    CounterScreen(
        vm.viewStates.subscribeAsState(initial = CounterViewState(0)),
        onCounterClick = { vm.consume(ReduktorVm.RedUIEvent.AddCounter()) }
    )
}