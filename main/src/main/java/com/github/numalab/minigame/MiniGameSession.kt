package com.github.numalab.minigame

import com.github.numalab.minigame.queue.MiniGameQueue

interface MiniGameSession {
    /**
     * ゲームのQueueを取得する
     * 現在ゲームが進行中の時はnull
     */
    fun queue():MiniGameQueue?
}