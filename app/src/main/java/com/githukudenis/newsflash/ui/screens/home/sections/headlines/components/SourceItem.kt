package com.githukudenis.newsflash.ui.screens.home.sections.headlines.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
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
    Box(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .clickable(onClick = onSelect)
            .border(
                border = BorderStroke(
                    width = if (selected) 1.dp else 0.dp,
                    color = if (selected) MediumBlue else Color.Transparent
                ),
                shape = RoundedCornerShape(corner = CornerSize(32.dp))
            ), contentAlignment = Alignment.Center
    ) {
        Text(
            text = source.name, color = textColor.value, style = TextStyle(
                fontSize = 12.sp,
                color = if (selected) Color.Black else Color.Black.copy(alpha = 0.6f)
            ),
            modifier = modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        )
    }
}
