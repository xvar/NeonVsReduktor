package com.github.xvar.neon.reduktor.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Скопировано из jetcaster (google sample)
 * Необходимо будет придумать, как подружить DI (кроме hilt) + Compose
 */

/**
 * Returns a [ViewModelProvider.Factory] which will return the result of [create] when it's
 * [ViewModelProvider.Factory.create] function is called.
 *
 * If the created [ViewModel] does not match the requested class, an [IllegalArgumentException]
 * exception is thrown.
 */
fun <VM : ViewModel> viewModelProviderFactoryOf(
    create: () -> VM
): ViewModelProvider.Factory = SimpleFactory(create)

/**
 * This needs to be a named class currently to workaround a compiler issue: b/163807311
 */
private class SimpleFactory<VM : ViewModel>(
    private val create: () -> VM
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val vm = create()
        if (modelClass.isInstance(vm)) {
            @Suppress("UNCHECKED_CAST")
            return vm as T
        }
        throw IllegalArgumentException("Can not create ViewModel for class: $modelClass")
    }
}