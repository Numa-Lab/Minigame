package com.github.numalab.minigame.queue

import org.bukkit.entity.Player

interface MiniGameQueue {
    /**
     * キューにプレイヤーを追加する
     * @return 追加に成功したかどうか(もうすでにキューに入っている場合はfalse)
     */
    fun join(p: Player) : Boolean

    // キューからプレイヤーを削除する
    fun cancel(p: Player)

    // キューの状態を取得する
    fun state(): QueueState

    /**
     * stateが
     * QueueState.WaitingForPlayersの時は開始予定時間(tick)を取得する
     * QueueState.Startingの時は実際の開始時間(tick)を取得する
     */
    fun startTime(): Long?

    // 現在のキューに含まれるプレイヤーを取得する
    fun getPlayers(): List<Player>

    // このキューの最大人数を取得する
    fun maxPlayers(): Int

    // このキューの最小人数を取得する
    fun minPlayers(): Int
}

enum class QueueState {
    WaitingForPlayers,
    Starting;
}