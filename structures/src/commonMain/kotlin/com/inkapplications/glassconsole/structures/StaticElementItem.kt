package com.inkapplications.glassconsole.structures

import ink.ui.structures.Positioning
import ink.ui.structures.elements.UiElement

data class StaticElementItem(
    val element: UiElement.Static,
    override val position: Positioning,
    override val span: Int,
): DisplayItem
