package com.github.numalab.minigame.util

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.Instrument
import org.bukkit.Note
import org.bukkit.entity.Player

fun Player.playSound(soundKey: String) {
    this.playSound(sound(soundKey))
}

fun sound(soundKey: String, volume: Float = 0.0f): Sound {
    return Sound.sound(Key.key("minecraft:$soundKey"), Sound.Source.MASTER, volume, 0.0f)
}


fun Player.playNote(note: Note, ins: Instrument = Instrument.PIANO) {
    this.playNote(this.location, ins, note)
}

fun Player.noticeSound() = playNote(Note(1))
