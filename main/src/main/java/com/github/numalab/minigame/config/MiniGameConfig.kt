package com.github.numalab.minigame.config

import net.kunmc.lab.configlib.BaseConfig
import org.bukkit.plugin.Plugin

class MiniGameConfig(plugin: Plugin) : BaseConfig(plugin) {
    val tntRun = TNTRunConfig(plugin)
}