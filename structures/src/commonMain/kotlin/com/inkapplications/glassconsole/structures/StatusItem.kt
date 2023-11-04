package com.inkapplications.glassconsole.structures

/**
 * A label with an indicator to show the status of something.
 */
data class StatusItem(
    override val span: Int,
    override val position: Position,
    val text: String,
    val indicator: Indicator,
): DisplayItem
