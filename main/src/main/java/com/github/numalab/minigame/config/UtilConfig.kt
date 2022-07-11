package com.github.numalab.minigame.config

import net.kunmc.lab.configlib.BaseConfig
import net.kunmc.lab.configlib.value.IntegerValue
import org.bukkit.plugin.Plugin

class UtilConfig(plugin: Plugin) : BaseConfig(plugin) {
    val actionbarHolderUpdateInterval = IntegerValue(20, 0, Int.MAX_VALUE)
}