package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.client.ActionClient
import com.inkapplications.glassconsole.structures.*
import ink.ui.structures.Positioning
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.*
import ink.ui.structures.layouts.CenteredElementLayout
import ink.ui.structures.layouts.FixedGridLayout
import ink.ui.structures.layouts.UiLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UiLayoutFactory(
    private val actionClient: ActionClient,
    private val actionScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) {
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
            is ScreenState.NoData -> CenteredElementLayout(
                body = ElementList(
                    items = listOf(
                        ThrobberElement(
                            caption = "No data",
                        ),
                        TextElement(
                            text = state.ips,
                        ),
                    ),
                    positioning = Positioning.Center,
                ),
            )
            is ScreenState.Configured -> when (val layout = state.config.layout) {
                is LayoutType.VerticalGrid -> {
                    val headings = FixedGridLayout.GridItem(
                        span = layout.columns,
                        horizontalPositioning = Positioning.Center,
                        body = StatusIndicatorElement(
                            text = "Not Connected",
                            sentiment = Sentiment.Caution,
                        )
                    ).takeIf { !state.connected }.let(::listOf).filterNotNull()
                    val gridItems = state.config.items.map { item ->
                        FixedGridLayout.GridItem(
                            span = item.span,
                            body = item.toUiElement(),
                            horizontalPositioning = item.position,
                        )
                    }
                    FixedGridLayout(
                        columns = layout.columns,
                        items = headings + gridItems,
                    )
                }
            }
        }
    }

    private fun DisplayItem.toUiElement(): UiElement {
        return when (this) {
            is ButtonItem -> ButtonElement(
                text = text,
                sentiment = sentiment ,
                latchOnPress = latching,
                onClick = {
                    actionScope.launch {
                        actionClient.sendAction(action)
                    }
                },
            )
            is StaticElementItem -> element
        }
    }
}
