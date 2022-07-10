package com.github.numalab.minigame.config

import net.kunmc.lab.configlib.BaseConfig
import net.kunmc.lab.configlib.value.IntegerValue
import net.kunmc.lab.configlib.value.LocationValue
import net.kunmc.lab.configlib.value.collection.ListValue
import org.bukkit.Location
import org.bukkit.plugin.Plugin

class TNTRunConfig(plugin: Plugin) : SimpleQueueConfig, BaseConfig(plugin, true) {
    override val joinLocation: LocationValue = LocationValue()
    override val cancelLocation: LocationValue = LocationValue()
    override val minPlayerCount: IntegerValue = IntegerValue(3)
    override val maxPlayerCount: IntegerValue = IntegerValue(12)
    override val startTimeInterval: IntegerValue = IntegerValue(30 * 20)

    fun startLocations(playerCount:Int):List<Location>{
        // TODO
    }
}