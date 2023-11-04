package com.inkapplications.glassconsole.structures

/**
 * A button that can be pressed to invoke a particular action.
 */
data class ButtonItem(
    override val span: Int,
    override val position: Position,
    val text: String,
    val action: Action,
    val latching: Boolean,
    val indicatorColor: Indicator,
): DisplayItem
