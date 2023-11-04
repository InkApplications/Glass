package com.inkapplications.glassconsole.structures

/**
 * A label with an indicator to show the status of something.
 */
data class StatusItem(
    override val span: Int = 1,
    override val position: Position = Position.Default,
    val text: String,
    val indicator: Indicator,
): DisplayItem
