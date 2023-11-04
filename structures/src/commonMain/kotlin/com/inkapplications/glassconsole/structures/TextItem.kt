package com.inkapplications.glassconsole.structures

sealed interface TextItem: DisplayItem {
    val text: String

    /**
     * Large header text
     */
    data class H1(
        override val text: String,
        override val span: Int,
    ) : TextItem

    /**
     * Medium header text
     */
    data class H2(
        override val text: String,
        override val span: Int,
    ) : TextItem

    /**
     * Small header text
     */
    data class H3(
        override val text: String,
        override val span: Int,
    ) : TextItem

    /**
     * Normal body text.
     */
    data class Body(
        override val text: String,
        override val span: Int,
    ) : TextItem
}
