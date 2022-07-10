package com.github.numalab.minigame.command

import com.github.numalab.minigame.MiniGamePlugin
import net.kunmc.lab.commandlib.Command

class MiniGameCommand(plugin:MiniGamePlugin) : Command("mg") {
    init {
        addChildren(listOf(
            MiniGameJoinCommand(plugin)
        ))
    }
}
