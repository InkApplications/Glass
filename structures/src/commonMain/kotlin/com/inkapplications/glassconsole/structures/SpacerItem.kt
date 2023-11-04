package com.inkapplications.glassconsole.structures

/**
 * Blank space used to separate elements.
 */
data class SpacerItem(
    override val span: Int = 1,
    override val position: Position = Position.Default,
): DisplayItem
