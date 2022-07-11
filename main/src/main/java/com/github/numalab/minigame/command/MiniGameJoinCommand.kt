package com.github.numalab.minigame.command

import com.github.numalab.minigame.MiniGame
import com.github.numalab.minigame.MiniGamePlugin
import com.github.numalab.minigame.util.info
import dev.kotx.flylib.command.Command

class MiniGameJoinCommand : Command("join") {
    init {
        description("To Join MiniGame")
        usage {
            selectionArgument("MiniGameName", MiniGame.values().map { it.name })
            executes {
                if (this.player == null) {
                    fail("You must be a player to execute this command")
                    return@executes
                } else {
                    val gameName = typedArgs[0] as String
                    val game = MiniGame.values().find { it.name == gameName }
                    if (game == null) {
                        fail("MiniGameNameが存在しません")
                        return@executes
                    } else {
                        val queue = game.instance(plugin as MiniGamePlugin).queue()
                        if (queue != null) {
                            val b = queue.join(this.player!!)
                            if (b) {
                                info("${game.name}に参加しました")
                            } else {
                                fail("${game.name}に参加できませんでした")
                            }
                        } else {
                            fail("${game.displayName}は現在参加できません")
                        }
                    }
                }
            }
        }
    }
}