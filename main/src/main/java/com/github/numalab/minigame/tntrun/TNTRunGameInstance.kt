package com.github.numalab.minigame.tntrun

import com.github.numalab.minigame.MiniGamePlugin
import com.github.numalab.minigame.instance.SimpleMiniGameInstance
import com.github.numalab.minigame.util.info
import com.github.numalab.minigame.util.text
import net.kyori.adventure.title.Title
import org.bukkit.entity.Player

class TNTRunGameInstance(val plugin: MiniGamePlugin, override val players: List<Player>) : SimpleMiniGameInstance(
    { count, index, _ -> plugin.config.tntRun.startLocations(count)[index] },
    players = players
) {
    override fun preparePlayer(p: Player) {
        p.inventory.clear()
        p.sendMessage(info("TNTRun開始!"))
    }

    override fun onStart() {
        players.forEach {
            it.showTitle(Title.title(text("TNTRun開始!"), text("人数: ${players.size}")))
        }
    }
}