package com.github.numalab.minigame.util

import org.bukkit.Location
import kotlin.math.cos
import kotlin.math.sin

fun Location.circle(radius: Double, times: Int): List<Location> {
    val list = mutableListOf<Location>()
    for (i in 0 until times) {
        val x = this.x + radius * cos(2.0 * Math.PI * i / times)
        val z = this.z + radius * sin(2.0 * Math.PI * i / times)
        val y = this.y
        list.add(Location(this.world, x, y, z))
    }
    return list
}