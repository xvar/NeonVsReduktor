package com.github.xvar.neon.reduktor.domain.reduktor

import ru.g000sha256.reduktor.Reducer

class ReduktorReducer : Reducer<ReduktorAction, ReduktorState> {

    override fun reduce(action: ReduktorAction, state: ReduktorState): ReduktorState {
        return when(action) {
            is ReduktorAction.Init -> {
                state.copy(
                    isInitial = false,
                    isLoading = false,
                    counter = action.data.initCounter,
                    title = action.data.initText
                )
            }
            is ReduktorAction.Data -> {
                val counter = action.counter
                val title = "Button hit for $counter times"
                state.copy(
                    isInitial = false,
                    counter = counter,
                    title = title,
                    isLoading = false
                )
            }
            is ReduktorAction.StartLoading -> state.copy(
                isInitial = false,
                isLoading = true
            )
            else -> state
        }
    }

}