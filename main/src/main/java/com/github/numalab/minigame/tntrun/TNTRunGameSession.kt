package com.github.numalab.minigame.tntrun

import com.github.numalab.minigame.MiniGamePlugin
import com.github.numalab.minigame.MiniGameSession
import com.github.numalab.minigame.queue.MiniGameQueue
import com.github.numalab.minigame.queue.SimpleMiniGameQueue
import org.bukkit.entity.Player

class TNTRunGameSession(val plugin: MiniGamePlugin) : MiniGameSession {
    companion object {
        private var instance: TNTRunGameSession? = null
        fun getInstance(plugin: MiniGamePlugin): TNTRunGameSession {
            if (instance == null) {
                instance = TNTRunGameSession(plugin)
            }
            return instance!!
        }
    }

    init {
        generateQueue()
    }

    private var que: MiniGameQueue? = null

    private fun generateQueue() {
        que = SimpleMiniGameQueue(plugin.config.tntRun, { onStart(it) }, plugin)
    }

    override fun queue(): MiniGameQueue? = que

    private fun onStart(players: List<Player>) {
        que = null  // Startしたので
        TNTRunGameInstance(plugin, players).start()
    }
}