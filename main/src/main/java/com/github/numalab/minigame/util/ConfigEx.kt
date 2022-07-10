package com.github.numalab.minigame.util

import net.kunmc.lab.configlib.Value

/**
 * Javaの記法がうまく認識されないが、一部のValueはnullになる可能性がある
 */
fun <T : Any, L : Value<T, L>> Value<T, L>.valueSafe(): T? {
    return this.value()
}

fun <T : Value<*, *>> Any.listValue(clazz: Class<T>): List<Pair<String, T>> {
    return this::class.java.declaredFields.map {
        it.isAccessible = true
        val name = it.name
        name to it
    }.filter {
        it.second.type == clazz
    }.map {
        it.first to it.second.get(this) as T // Checked
    }
}