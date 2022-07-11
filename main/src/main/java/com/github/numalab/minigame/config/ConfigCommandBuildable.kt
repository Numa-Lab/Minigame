package com.github.numalab.minigame.config

import net.kunmc.lab.configlib.ConfigCommand

interface ConfigCommandBuildable {
    fun build(): ConfigCommand
}