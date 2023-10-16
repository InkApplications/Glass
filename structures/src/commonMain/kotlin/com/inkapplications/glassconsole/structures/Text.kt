package com.inkapplications.glassconsole.structures

/**
 * Large header text
 */
data class H1(
    val text: String,
): DisplayItem

/**
 * Medium header text
 */
data class H2(
    val text: String,
): DisplayItem

/**
 * Small header text
 */
data class H3(
    val text: String,
): DisplayItem

/**
 * Normal body text.
 */
data class Body(
    val text: String,
): DisplayItem
