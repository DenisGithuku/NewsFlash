package com.githukudenis.newsflash.ui.screens.articles.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.githukudenis.newsflash.ui.theme.MediumBlue
import com.githukudenis.newsflash.ui.util.ActiveScreen
import java.util.*

@Composable
fun ActiveScreenItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onSelect: () -> Unit,
    activeScreen: ActiveScreen,
) {
    val textColor = animateColorAsState(targetValue = if (selected) Color.Black else Color.Black.copy(alpha = 0.5f))
    Column(
        modifier = modifier
            .padding(end = 10.dp)
            .clickable(onClick = onSelect),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildString {
                append(activeScreen.name.first())
                append(
                    activeScreen.name.substringAfter(activeScreen.name.first())
                        .lowercase(Locale.ROOT)
                )
            }, style = MaterialTheme.typography.subtitle2.copy(
                color = textColor.value, fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.W500 else FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        AnimatedVisibility(
            visible = selected, enter = fadeIn() + expandHorizontally(
                animationSpec = tween(
                    durationMillis = 500, easing = LinearOutSlowInEasing
                )
            ), exit = fadeOut() + shrinkHorizontally(
                animationSpec = tween(
                    durationMillis = 500, easing = FastOutSlowInEasing
                )
            )
        ) {
            Canvas(modifier = modifier.size(width = 40.dp, height = 4.dp)) {
                drawRoundRect(
                    color = MediumBlue,
                    cornerRadius = CornerRadius(x = 10f, y = 10f)
                )
            }
        }
    }
}
