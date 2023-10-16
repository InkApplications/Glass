package com.inkapplications.glassconsole.structures

/**
 * A label with an indicator to show the status of something.
 */
data class Status(
    val text: String,
    val indicator: Indicator,
): DisplayItem
