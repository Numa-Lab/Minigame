package com.github.numalab.minigame.queue

import com.github.numalab.minigame.MiniGamePlugin
import com.github.numalab.minigame.config.SimpleQueueConfig
import com.github.numalab.minigame.util.info
import com.github.numalab.minigame.util.text
import com.github.numalab.minigame.util.valueSafe
import net.kunmc.lab.configlib.value.IntegerValue
import net.kunmc.lab.configlib.value.LocationValue
import org.bukkit.entity.Player

class SimpleMiniGameQueue(
    val joinLocation: LocationValue,
    val cancelLocation: LocationValue,
    private val minPlayerCount: IntegerValue,
    private val maxPlayerCount: IntegerValue,
    private val startTimeInterval: IntegerValue,
    val onStart: (List<Player>) -> Unit,
    val plugin: MiniGamePlugin
) : MiniGameQueue {
    constructor(config: SimpleQueueConfig, onStart: (List<Player>) -> Unit, plugin: MiniGamePlugin) : this(
        config.joinLocation,
        config.cancelLocation,
        config.minPlayerCount,
        config.maxPlayerCount,
        config.startTimeInterval,
        onStart,
        plugin
    )


    init {
        updateStartTime(plugin.server.currentTick + (startTimeInterval.valueSafe()!!.toLong()))
    }

    private val queue = mutableListOf<Player>()

    override fun join(p: Player) {
        if (getPlayers().size > maxPlayers()) {
            p.sendMessage(info("満員です"))
            return
        }
        queue.add(p)
        p.teleport(joinLocation.valueSafe()!!)
        p.inventory.clear()
        // TODO Give Cancel Item, etc.
    }

    override fun cancel(p: Player) {
        queue.remove(p)
        p.teleport(cancelLocation.valueSafe()!!)
    }

    override fun state(): QueueState {
        return if (getPlayers().size >= minPlayers()) {
            QueueState.Starting
        } else {
            QueueState.WaitingForPlayers
        }
    }

    private fun updateStartTime(to: Long) {
        startTimeLong = to
        plugin.server.scheduler.runTaskLater(plugin, Runnable {
            onTime()
        }, to - plugin.server.currentTick)
    }

    private var startTimeLong: Long = -1L
    override fun startTime(): Long? {
        if (startTimeLong == -1L) {
            return null
        }
        return startTimeLong
    }

    override fun getPlayers(): List<Player> {
        return queue.toList().filter { it.isOnline }
    }

    override fun maxPlayers(): Int = maxPlayerCount.valueSafe()!!
    override fun minPlayers(): Int = minPlayerCount.valueSafe()!!

    // 開始予定時刻になった
    private fun onTime() {
        when (state()) {
            QueueState.WaitingForPlayers -> {
                // TODO プレイヤー不足のため時間を延長
                updateStartTime(plugin.server.currentTick + (startTimeInterval.valueSafe()!!.toLong()))
            }
            QueueState.Starting -> {
                onStart(getPlayers())
            }
        }
    }
}