package com.githukudenis.newsflash.ui.screens.headlines.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.githukudenis.newsflash.domain.model.SourceX
import com.githukudenis.newsflash.ui.theme.MediumBlue

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SourceItem(
    source: SourceX,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor = animateColorAsState(
        targetValue = if (selected) Color.Black else Color.Black.copy(
            alpha = 0.8f
        )
    )
    val textSize = animateFloatAsState(targetValue = if (selected) 14f else 12f)
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 10.dp).clickable {
            onSelect()
        }) {
        Text(
            text = source.name, color = textColor.value, style = TextStyle(
                fontSize = TextUnit(
                    textSize.value, type = TextUnitType.Sp
                )
            )
        )
        AnimatedVisibility(
            visible = selected,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) {
            Canvas(modifier = modifier.size(width = 20.dp, height = 4.dp)) {
                drawRoundRect(
                    color = MediumBlue,
                    size = size,
                    cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx())
                )
            }
        }
    }
}
