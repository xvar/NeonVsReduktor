package com.github.xvar.neon.reduktor.domain.neon

import com.sch.neon.Effect

sealed class NeonEffect : Effect() {
    class LoadNextPage(val currentCounter: Int) : NeonEffect()
}