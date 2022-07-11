package com.github.numalab.minigame.util

import com.github.numalab.minigame.MiniGamePlugin
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

fun Player.actionBar(comp: Component, overwriteHolder: MiniGamePlugin? = null) {
    overwriteHolder?.utils?.actionBarHolderUtil?.removeHolder(this)
    this.sendActionBar(comp)
}

fun Player.resetActionBarHolder(plugin: MiniGamePlugin) = actionBar(Component.empty(), plugin)

fun Player.actionBarHold(plugin: MiniGamePlugin, comp: Component) =
    plugin.utils.actionBarHolderUtil.setHolder(this, comp)

class ActionBarHolderUtil private constructor(val plugin: MiniGamePlugin) {
    companion object {
        private var instance: ActionBarHolderUtil? = null
        fun getInstance(plugin: MiniGamePlugin): ActionBarHolderUtil {
            if (instance == null) {
                instance = ActionBarHolderUtil(plugin)
            }
            return instance!!
        }
    }

    init {
        instance = this
    }

    private val actionBarHolders = mutableMapOf<Player, Component>()

    fun setHolder(player: Player, comp: Component) {
        actionBarHolders[player] = comp
        player.sendActionBar(comp)
        ensureUpdate()
    }

    fun removeHolder(player: Player) {
        actionBarHolders.remove(player)
    }

    private var repeatingTask: BukkitTask? = null

    private fun ensureUpdate() {
        if (repeatingTask == null) {
            repeatingTask = plugin.server.scheduler.runTaskLater(
                plugin,
                Runnable { update() },
                plugin.config.utilConfig.actionbarHolderUpdateInterval.value().toLong()
            )
        }
    }

    private fun update() {
        repeatingTask = null
        sendActionBars()
        ensureUpdate()
    }

    private fun sendActionBars() {
        actionBarHolders.forEach { (player, comp) ->
            player.actionBar(comp)
        }
    }
}