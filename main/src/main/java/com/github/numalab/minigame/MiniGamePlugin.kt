package com.github.numalab.minigame

import com.github.numalab.minigame.command.MiniGameCommand
import com.github.numalab.minigame.config.MiniGameConfig
import net.kunmc.lab.commandlib.CommandLib
import org.bukkit.plugin.java.JavaPlugin

class MiniGamePlugin : JavaPlugin() {
    lateinit var config: MiniGameConfig
    override fun onEnable() {
        // Plugin startup logic
        config = MiniGameConfig(this)
        CommandLib.register(this, MiniGameCommand(this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}