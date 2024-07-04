package com.inkapplications.glassconsole.elements

import ink.ui.structures.elements.UiElement

/**
 * An interactive 0-9 pin pad for entering private codes.
 */
data class PinPadElement(
    val value: String,
    val onKeyPress: (String) -> Unit,
    val onEnter: () -> Unit,
    val onClear: () -> Unit,
): UiElement.Interactive
