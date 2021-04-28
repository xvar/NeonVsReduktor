package com.github.xvar.neon.reduktor.ui.util

import android.util.Log
import com.github.xvar.neon.reduktor.BuildConfig

fun <T> T.instanceDebug(where: String? = "") : T {
    return this.also {
        if (BuildConfig.DEBUG) {
            Log.d("compose_debug", "[$this : ${System.identityHashCode(this)}] in $where")
        }
    }
}