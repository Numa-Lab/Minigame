package com.github.numalab.minigame.config

import net.kunmc.lab.configlib.BaseConfig
import net.kunmc.lab.configlib.ConfigCommand
import net.kunmc.lab.configlib.ConfigCommandBuilder
import org.bukkit.plugin.Plugin

class MiniGameConfig(plugin: Plugin) : BaseConfig(plugin), ConfigCommandBuildable {
    val tntRun = TNTRunConfig(plugin)

    override fun build(): ConfigCommand {
        return ConfigCommandBuilder(this)
            .addConfig(tntRun)
            .build()
    }
}