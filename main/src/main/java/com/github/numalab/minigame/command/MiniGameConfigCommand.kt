package com.github.numalab.minigame.command

import com.github.numalab.minigame.MiniGamePlugin
import com.github.numalab.minigame.util.info
import com.github.numalab.minigame.util.listValue
import net.kunmc.lab.configlib.value.LocationValue
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MiniGameConfigCommand(val plugin: MiniGamePlugin) {
    fun config(sender: CommandSender, args: Array<out String>): Boolean {
        when (args.size) {
            2 -> {
                val value = list().find { it.first == args[1] }
                if (value == null) {
                    sender.sendMessage(com.github.numalab.minigame.util.error("Unknown config key: ${args[1]}"))
                    return true
                } else {
                    if (sender is Player) {
                        value.second.value(sender.location.clone())
                        sender.sendMessage(info("Set ${args[1]} to Your location"))
                        return true
                    }
                    sender.sendMessage(com.github.numalab.minigame.util.error("Only player can set config"))
                    return true
                }
            }

            else -> {
                return false
            }
        }
    }

    private fun list(): List<Pair<String, LocationValue>> {
        return plugin.config.tntRun.listValue(LocationValue::class.java)
    }

    fun tabComplete(sender: CommandSender, commandLabel: String, args: Array<out String>): List<String> {
        return when (args.size) {
            2 -> list().filter { it.first.startsWith(args[1]) }.map { it.first }
            else -> emptyList()
        }
    }
}