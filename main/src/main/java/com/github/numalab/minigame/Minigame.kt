package com.github.numalab.minigame

import com.github.numalab.minigame.tntrun.TNTRunGameSession

enum class MiniGame(val instance: (MiniGamePlugin) -> MiniGameSession, val displayName: String) {
    TNTRun({ TNTRunGameSession.getInstance(it) }, "TNT Run");
}