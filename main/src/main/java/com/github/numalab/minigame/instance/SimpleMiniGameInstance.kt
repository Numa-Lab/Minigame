package com.github.numalab.minigame.instance

import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * @param startLocation (AllPlayerCount,PlayerIndex,Player) -> SpawnLocation
 */
abstract class SimpleMiniGameInstance(
    val startLocation: (Int, Int, Player) -> Location,
    override val players: List<Player>
) : MiniGameInstance {
    override fun start() {
        players.forEachIndexed { index, player ->
            player.teleport(startLocation(players.size, index, player))
        }

        players.forEach {
            preparePlayer(it)
        }

        onStart()
    }

    abstract fun preparePlayer(p: Player)
    abstract fun onStart()
}