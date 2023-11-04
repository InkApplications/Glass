package com.inkapplications.glassconsole.structures

/**
 * An element that can span multiple columns or rows.
 *
 * This is used to change the relative width of an element when placed
 * in a grid layout.
 */
interface Spanable {
    val span: Int
}
