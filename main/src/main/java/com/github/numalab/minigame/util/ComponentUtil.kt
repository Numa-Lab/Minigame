package com.github.numalab.minigame.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor

fun text(text: String, color: NamedTextColor = NamedTextColor.WHITE): TextComponent {
    return Component.text(text, color)
}

fun info(text: String) = text(text, NamedTextColor.AQUA)

fun error(text: String) = text(text, NamedTextColor.RED)

operator fun Component.plus(other: Component): Component {
    return this.append(other)
}