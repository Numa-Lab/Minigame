package com.github.numalab.minigame

import com.github.numalab.minigame.command.MiniGameCommand
import com.github.numalab.minigame.config.MiniGameConfig
import com.github.numalab.minigame.util.Utils
import dev.kotx.flylib.flyLib
import net.kunmc.lab.configlib.ConfigCommandBuilder
import org.bukkit.plugin.java.JavaPlugin

class MiniGamePlugin : JavaPlugin() {
    lateinit var utils: Utils
    lateinit var config: MiniGameConfig
    override fun onEnable() {
        // Plugin startup logic
        config = MiniGameConfig(this)
        config.saveConfigIfAbsent()
        config.loadConfig()

        utils = Utils(this)

        flyLib {
            command(MiniGameCommand(config))
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
        config.saveConfigIfPresent()
    }
}