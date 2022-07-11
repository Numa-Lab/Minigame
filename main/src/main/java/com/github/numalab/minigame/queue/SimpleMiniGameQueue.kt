package com.github.numalab.minigame.queue

import com.github.numalab.minigame.MiniGamePlugin
import com.github.numalab.minigame.config.SimpleQueueConfig
import com.github.numalab.minigame.util.*
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


    private var startTimeLong: Long = -1L
    override fun startTime(): Long? {
        if (startTimeLong == -1L) {
            return null
        }
        return startTimeLong
    }

    init {
        updateStartTime(plugin.server.currentTick + (startTimeInterval.valueSafe()!!.toLong()))
    }

    private val queue = mutableListOf<Player>()

    override fun join(p: Player): Boolean {
        if (getPlayers().size > maxPlayers()) {
            p.sendMessage(info("満員です"))
            return false
        }
        if (queue.contains(p)) {
            p.sendMessage(info("すでに参加しています"))
            return false
        }
        queue.add(p)
        p.teleport(joinLocation.valueSafe()!!)
        p.inventory.clear()
        p.noticeSound()
        updateActionBarHolder()
        p.actionBar(info("参加しました"))
        // TODO Give Cancel Item, etc.
        return true
    }

    override fun cancel(p: Player) {
        queue.remove(p)
        p.actionBar(error("キャンセルしました"), plugin)
        updateActionBarHolder()
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
                getPlayers().forEach { it.sendMessage(info("プレイヤーが不足しています")) }
                updateStartTime(plugin.server.currentTick + (startTimeInterval.valueSafe()!!.toLong()))
            }
            QueueState.Starting -> {
                getPlayers().forEach { it.actionBar(info(""), plugin) } // ActionBarをクリア
                onStart(getPlayers())
            }
        }
    }

    private fun updateActionBarHolder() {
        val size = getPlayers().size
        val time = (startTime()!! - plugin.server.currentTick) / 20
        if (size < minPlayers()) {
            getPlayers().forEach { it.actionBarHold(plugin, info("参加を待っています [$size/${minPlayers()}]")) }
        } else {
            getPlayers().forEach { it.actionBarHold(plugin, info("開始を待っています [${time}秒]")) } // TODO Update Time Interval
        }
    }
}