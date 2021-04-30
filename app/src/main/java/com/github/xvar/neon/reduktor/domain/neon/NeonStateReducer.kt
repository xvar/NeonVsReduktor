package com.github.xvar.neon.reduktor.domain.neon

import com.sch.neon.StateReducer
import com.sch.neon.StateWithEffects

class NeonStateReducer : StateReducer<NeonAction, NeonState, NeonEffect> {

    //Общая проблема подобных библиотек!
    //В reduce такого рода (здесь и в RxRedux) есть недостаток - нужно
    //быть аккуратным, чтобы не допустить бесконечного увеличения цепочки (actions / effects)
    override fun reduce(
        state: NeonState,
        event: NeonAction
    ): StateWithEffects<NeonState, NeonEffect> {
        return when(event) {
            is NeonAction.Back -> StateWithEffects(state, emptyList())
            is NeonAction.Data -> StateWithEffects(
                state = state.copy(counter = event.counter, isLoading = false, isInitial = false),
                effects = emptyList()
            )
            is NeonAction.Error -> StateWithEffects(
                state = state.copy(throwable = event.error, isLoading = false, isInitial = false),
                effects = emptyList()
            )
            //Приходится передавать данные state через Effect (нет state-accessor)
            is NeonAction.Increment -> StateWithEffects(
                state = state.copy(isLoading = true),
                effects = listOf(NeonEffect.LoadNextPage(state.counter))
            )
            is NeonAction.Init -> StateWithEffects(
                state = state.copy(counter = 0, throwable = null, isLoading = false, isInitial = true),
                effects = emptyList()
            )
        }
    }

}