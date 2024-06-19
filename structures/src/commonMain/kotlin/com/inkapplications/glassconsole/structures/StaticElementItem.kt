package com.inkapplications.glassconsole.structures

import ink.ui.structures.Positioning
import ink.ui.structures.elements.UiElement

data class StaticElementItem(
    val element: UiElement.Static,
    override val position: Positioning,
    override val span: Int,
): DisplayItem

fun UiElement.Static.asDisplayItem(
    position: Positioning = Positioning.Start,
    span: Int = 1,
) = StaticElementItem(
    element = this,
    position = position,
    span = span,
)
