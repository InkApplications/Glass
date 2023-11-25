package com.inkapplications.glassconsole.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.inkapplications.glassconsole.structures.ButtonItem
import com.inkapplications.glassconsole.structures.DisplayItem
import com.inkapplications.glassconsole.structures.LayoutType
import com.inkapplications.glassconsole.structures.Position
import com.inkapplications.glassconsole.ui.theme.InkTheme

/**
 * UI Layout that displays items in a vertical grid.
 */
@Composable
fun VerticalGridItemLayout(
    layoutType: LayoutType.VerticalGrid,
    items: List<DisplayItem>,
    onButtonClick: (ButtonItem) -> Unit,
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(all = InkTheme.spacing.gutter - InkTheme.spacing.item),
        columns = GridCells.Fixed(layoutType.columns),
    ) {
        items(
            items = items,
            span = { item ->
                GridItemSpan(item.span)
            }
        ) { item ->
            Box(
                modifier = Modifier.padding(InkTheme.spacing.item),
                contentAlignment = when (item.position) {
                    Position.Centered -> Alignment.Center
                    else -> Alignment.TopStart
                }
            ) {
                Item(item, onButtonClick)
            }
        }
    }
}
