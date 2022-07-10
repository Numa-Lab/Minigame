package com.github.numalab.minigame.util

import net.kunmc.lab.configlib.Value

/**
 * Javaの記法がうまく認識されないが、一部のValueはnullになる可能性がある
 */
fun <T : Any, L : Value<T, L>> Value<T, L>.valueSafe(): T? {
    return this.value()
}