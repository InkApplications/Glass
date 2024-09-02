package com.inkapplications.glassconsole.renderer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.inkapplications.glassconsole.elements.PinPadElement
import ink.ui.render.compose.elements.Button
import ink.ui.render.compose.renderer.ElementRenderer
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

object PinPadRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        if (element !is PinPadElement) return RenderResult.Skipped

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicText(
                text = "*".repeat(element.value.length),
                maxLines = 1,
                modifier = Modifier.padding(theme.spacing.item),
                style = theme.typography.h2.copy(color = theme.colors.foreground),
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PinPadValueButton("1", theme, element.onKeyPress)
                PinPadValueButton("2", theme, element.onKeyPress)
                PinPadValueButton("3", theme, element.onKeyPress)
            }
            Row {
                PinPadValueButton("4", theme, element.onKeyPress)
                PinPadValueButton("5", theme, element.onKeyPress)
                PinPadValueButton("6", theme, element.onKeyPress)
            }
            Row {
                PinPadValueButton("7", theme, element.onKeyPress)
                PinPadValueButton("8", theme, element.onKeyPress)
                PinPadValueButton("9", theme, element.onKeyPress)
            }
            Row {
                PinPadButton(
                    symbol = Symbol.Close,
                    sentiment = Sentiment.Caution,
                    theme = theme,
                    onClick = element.onClear
                )
                PinPadValueButton("0", theme, element.onKeyPress)
                PinPadButton(
                    symbol = Symbol.Done,
                    sentiment = Sentiment.Primary,
                    theme = theme,
                    onClick = element.onEnter
                )
            }
        }

        return RenderResult.Rendered
    }

    @Composable
    private fun PinPadButton(
        text: String = "",
        symbol: Symbol? = null,
        sentiment: Sentiment = Sentiment.Nominal,
        theme: ComposeRenderTheme,
        onClick: () -> Unit,
    ) {
        Button(
            text = text,
            leadingSymbol = symbol,
            onClick = onClick,
            sentiment = sentiment,
            contentModifier = Modifier.size(40.dp),
            buttonModifier = Modifier.padding(theme.spacing.item),
            theme = theme,
        )
    }

    @Composable
    private fun PinPadValueButton(
        text: String,
        theme: ComposeRenderTheme,
        onClick: (String) -> Unit,
    ) {
        PinPadButton(
            text = text,
            theme = theme,
            onClick = { onClick(text) },
        )
    }
}
