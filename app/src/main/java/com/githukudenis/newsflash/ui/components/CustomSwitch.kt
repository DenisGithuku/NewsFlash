package com.githukudenis.newsflash.ui.components

import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    checkedTrackColor: Color = Color.Blue,
    height: Dp = 36.dp,
    width: Dp = 20.dp,
    border: Dp = 2.dp,
    uncheckedTrackColor: Color = Color.Black.copy(alpha = 0.2f),
    gapBetweenTrackAndThumb: Dp = 4.dp
) {

    val isChecked = remember {
        mutableStateOf(false)
    }

    val thumbRadius = (height / 2) - gapBetweenTrackAndThumb

    val color =
        animateColorAsState(targetValue = if (isChecked.value) checkedTrackColor else uncheckedTrackColor)

    val animatePosition =
        animateFloatAsState(targetValue = if (isChecked.value) with(LocalDensity.current) {
            (width - gapBetweenTrackAndThumb - thumbRadius).toPx()
        }
        else with(LocalDensity.current) {
            (width - gapBetweenTrackAndThumb).toPx()
        })


    Canvas(modifier = modifier) {
        drawRoundRect(
            color = if (isChecked.value) checkedTrackColor else uncheckedTrackColor,
            size = Size(width = width.toPx(), height = height.toPx()),
            style = Stroke(
                width = border.toPx(), cap = StrokeCap.Round
            ),
        )

        drawCircle(
            center = Offset(
                x = size.width - gapBetweenTrackAndThumb.toPx(),
                y = size.height / 2 - gapBetweenTrackAndThumb.toPx()
            ),
            radius = height.toPx(),
            color = color.value
        )
    }

}


@Preview(
    showSystemUi = true, uiMode = UI_MODE_TYPE_NORMAL, device = "id:pixel_5"
)
@Composable
fun CustomSwitchPreview() {
    CustomSwitch()
}
