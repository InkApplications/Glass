package com.inkapplications.glassconsole.structures

import ink.ui.structures.Positioning
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol

/**
 * A button that can be pressed to invoke a particular action.
 */
data class ButtonItem(
    override val span: Int = 1,
    override val position: Positioning = Positioning.Start,
    val text: String,
    val action: Action,
    val latching: Boolean,
    val sentiment: Sentiment,
    val leadingSymbol: Symbol? = null,
    val trailingSymbol: Symbol? = null,
): DisplayItem
