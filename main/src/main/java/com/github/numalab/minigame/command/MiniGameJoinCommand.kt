package com.github.numalab.minigame.command

import com.github.numalab.minigame.MiniGame
import com.github.numalab.minigame.MiniGamePlugin
import com.github.numalab.minigame.util.info
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MiniGameJoinCommand(val plugin: MiniGamePlugin) {
    fun join(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (args.size == 2) {
                val game = MiniGame.values().find { g -> g.name == args[1] }
                if (game == null) {
                    sender.sendMessage(com.github.numalab.minigame.util.error("MiniGameNameが存在しません"))
                } else {
                    val queue = game.instance(plugin).queue()
                    if (queue == null) {
                        sender.sendMessage(com.github.numalab.minigame.util.error("${game.displayName}は現在参加できません"))
                    } else {
                        queue.join(sender)
                        sender.sendMessage(info("${game.displayName}に参加しました"))
                    }
                }
                return true
            } else {
                return false
            }
        } else {
            sender.sendMessage(com.github.numalab.minigame.util.error("コマンドはプレイヤーから実行してください"))
            return true
        }
    }

    fun tabComplete(sender: CommandSender, commandLabel: String, args: Array<out String>): List<String> {
        return when (args.size) {
            2 -> MiniGame.values().map { it.name }.filter { it.startsWith(args[1]) }
            else -> emptyList()
        }
    }
}