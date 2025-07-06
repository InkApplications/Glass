package com.inkapplications.glassconsole

import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.*
import ink.ui.structures.layouts.CenteredElementLayout
import ink.ui.structures.layouts.UiLayout

class UiLayoutFactory {
    fun forState(state: ScreenState): UiLayout {
        return when (state) {
            ScreenState.Initial -> CenteredElementLayout(
                body = ThrobberElement(
                    caption = "Initializing...",
                ),
            )
            ScreenState.NoConnection -> CenteredElementLayout(
                body = TextElement(
                    text = "No internet connection",
                ),
            )
            is ScreenState.Connected -> CenteredElementLayout(
                body = ElementList(
                    items = listOf(
                        ThrobberElement(
                            caption = "No data",
                        ),
                        TextElement(
                            text = state.ips.joinToString(),
                        ),
                    ),
                    positioning = Positioning.Center,
                ),
            )
            is ScreenState.ShowPsk -> CenteredElementLayout(
                body = ElementList(
                    items = listOf(
                        TextElement(
                            text = "New PSK",
                            style = TextStyle.H1,
                        ),
                        EmptyElement,
                        TextElement(
                            text = state.psk.value,
                            style = TextStyle.Body,
                        ),
                        ElementList(
                            items = listOf(
                                TextElement(
                                    text = "Save this PSK to use the PIN functionality.",
                                    style = TextStyle.Caption,
                                ),
                                TextElement(
                                    text = "You will not be able to access it again.",
                                    style = TextStyle.Caption,
                                ),
                            ),
                            positioning = Positioning.Center,
                            groupingStyle = GroupingStyle.Unified,
                        ),
                        ButtonElement(
                            text = "Continue",
                            onClick = {
                                state.onDismiss()
                            }
                        )
                    ),
                    positioning = Positioning.Center,
                    groupingStyle = GroupingStyle.Sections,
                ),
            )
        }
    }
}
