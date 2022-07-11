package com.github.numalab.minigame.command

import com.github.numalab.minigame.config.MiniGameConfig
import dev.kotx.flylib.command.Command
import net.kunmc.lab.configlib.ConfigCommandBuilder

class MiniGameCommand(config:MiniGameConfig) : Command("mg") {
    init {
        description("Main command of MiniGame Plugin")
        children(
            MiniGameJoinCommand(),
            config.build()
        )
    }
}
