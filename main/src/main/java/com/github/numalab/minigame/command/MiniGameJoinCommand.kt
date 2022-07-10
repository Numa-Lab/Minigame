package com.github.numalab.minigame.command

import com.github.numalab.minigame.MiniGame
import com.github.numalab.minigame.MiniGamePlugin
import com.github.numalab.minigame.util.info
import net.kunmc.lab.commandlib.Command
import org.bukkit.entity.Player

class MiniGameJoinCommand(val plugin: MiniGamePlugin) : Command("join") {
    init {
        argument {
            it.literalArgument("MiniGameName", MiniGame.values().map { g -> g.displayName })
            it.execute { c ->
                if (c.parsedArgs.isEmpty()) {
                    c.sender.sendMessage(com.github.numalab.minigame.util.error("MiniGameNameを指定してください"))
                    return@execute
                }
                val p = c.sender
                if (p is Player) {
                    val game = MiniGame.values().find { g -> g.displayName == c.getParsedArg(0, String::class.java) }
                    if (game == null) {
                        p.sendMessage(com.github.numalab.minigame.util.error("MiniGameNameが存在しません"))
                        return@execute
                    } else {
                        val queue = game.instance(plugin).queue()
                        if (queue == null) {
                            p.sendMessage(com.github.numalab.minigame.util.error("${game.displayName}は現在参加できません"))
                            return@execute
                        } else {
                            queue.join(p)
                            p.sendMessage(info("${game.displayName}に参加しました"))
                        }
                    }
                } else {
                    c.sender.sendMessage(com.github.numalab.minigame.util.error("コマンドはプレイヤーから実行してください"))
                }
            }
        }
    }
}