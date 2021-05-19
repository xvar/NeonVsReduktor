package com.github.xvar.neon.reduktor.domain.rxredux

import com.freeletics.rxredux.Reducer

class RxReduxReducer : Reducer<RxReduxState, RxReduxAction> {

    override fun invoke(currentState: RxReduxState, newAction: RxReduxAction): RxReduxState {
        return when (newAction) {
            is RxReduxAction.Data -> currentState.copy(
                counter = newAction.counter,
                isLoading = false,
                isInitial = false,
                title = "RxRedux, we hit the button for the ${newAction.counter} times"
            )
            is RxReduxAction.Error -> currentState.copy(
                throwable = newAction.error
            )
            is RxReduxAction.Init -> currentState.copy(
                isInitial = true,
                isLoading = false,
                counter = newAction.data.startCounter
            )
            is RxReduxAction.Loading -> currentState.copy(
                isLoading = true
            )
            is RxReduxAction.Back -> currentState
            is RxReduxAction.Increment -> currentState
        }
    }

}