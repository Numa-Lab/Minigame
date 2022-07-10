package com.github.numalab.minigame.instance

import org.bukkit.entity.Player

interface MiniGameInstance {
    val players: List<Player>

    // ゲーム開始処理
    fun start()
}