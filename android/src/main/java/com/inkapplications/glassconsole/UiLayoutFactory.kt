package com.inkapplications.glassconsole

import com.inkapplications.glassconsole.client.ActionClient
import com.inkapplications.glassconsole.elements.HashingPinPadElement
import com.inkapplications.glassconsole.structures.*
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.Sentiment
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.*
import ink.ui.structures.layouts.CenteredElementLayout
import ink.ui.structures.layouts.FixedGridLayout
import ink.ui.structures.layouts.UiLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UiLayoutFactory(
    private val actionClient: ActionClient,
    private val json: Json,
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
                leadingSymbol = leadingSymbol,
                trailingSymbol = trailingSymbol,
            )
            is PinPadItem -> HashingPinPadElement(
                challengeNonce = challengeNonce,
                witness = witness,
                onSuccess = { response ->
                    actionScope.launch {
                        actionClient.sendAction(
                            Action.Post(
                                url = callbackUrl,
                                body = json.encodeToString(response)
                            )
                        )
                    }
                }
            )
            is StaticElementItem -> element
            else -> TextElement("Unsupported Element")
        }
    }
}
