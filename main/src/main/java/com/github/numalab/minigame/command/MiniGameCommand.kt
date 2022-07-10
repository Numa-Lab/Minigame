package com.github.numalab.minigame.command

import com.github.numalab.minigame.MiniGamePlugin
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class MiniGameCommand(plugin: MiniGamePlugin) : Command("mg") {
    init {
        plugin.server.commandMap.register("mg", this)
    }

    val join = MiniGameJoinCommand(plugin)
    val config = MiniGameConfigCommand(plugin)

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (args.isNotEmpty()) {
            return when (args[0]) {
                "join" -> join.join(sender, args)
                "config" -> config.config(sender, args)
                else -> false
            }
        }
        return false
    }

    override fun tabComplete(sender: CommandSender, commandLabel: String, args: Array<out String>): List<String> {
        if (args.isNotEmpty() && args[0].isNotEmpty()) {
            return when (args[0]) {
                "join" -> join.tabComplete(sender, commandLabel, args)
                "config" -> config.tabComplete(sender, commandLabel, args)
                else -> emptyList()
            }
        }
        return listOf("join", "config")
    }
}
