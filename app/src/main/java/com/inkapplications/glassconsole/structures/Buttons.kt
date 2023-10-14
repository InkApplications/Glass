package com.inkapplications.glassconsole.structures

/**
 * A button that can be pressed to invoke a particular action.
 */
data class Button(
    val text: String,
    val url: String,
): DisplayItem
