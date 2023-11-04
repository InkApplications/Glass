package com.inkapplications.glassconsole.structures

/**
 * A label with an indicator to show the status of something.
 */
data class StatusItem(
    override val span: Int,
    val text: String,
    val indicator: Indicator,
): DisplayItem
