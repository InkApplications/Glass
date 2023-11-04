package com.inkapplications.glassconsole.structures

/**
 * A button that can be pressed to invoke a particular action.
 */
data class ButtonItem(
    override val span: Int = 1,
    override val position: Position = Position.Default,
    val text: String,
    val action: Action,
    val latching: Boolean,
    val indicator: Indicator,
): DisplayItem
