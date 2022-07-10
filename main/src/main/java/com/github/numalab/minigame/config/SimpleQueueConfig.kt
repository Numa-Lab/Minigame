package com.github.numalab.minigame.config

import net.kunmc.lab.configlib.value.IntegerValue
import net.kunmc.lab.configlib.value.LocationValue

interface SimpleQueueConfig {
    val joinLocation:LocationValue
    val cancelLocation:LocationValue
    val minPlayerCount:IntegerValue
    val maxPlayerCount:IntegerValue
    val startTimeInterval:IntegerValue
}