package com.plcoding.stockmarketapp.presentation.company_info

import android.graphics.Color.*
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.stockmarketapp.domain.models.IntradayInfo
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun StockChart(
    infos: List<IntradayInfo> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {
    val spacing = 100f
    val transperantGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }
    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.close * 1.01f }?.toFloat() ?: 0f)
    }
    val lowerValue = remember(infos) {
        (infos.minOfOrNull { it.close }?.toFloat() ?: 0f)
    }
    val density = LocalDensity.current
    val textPaint = remember {
        Paint().apply {
            color = WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Canvas(modifier = modifier){
        val spacePerHour = (size.width - spacing) / infos.size
        (0 until infos.size - 1 step 2).forEach{i ->
            val info = infos[i]
            val hour = info.date.hour

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + i * spacePerHour,
                    size.height - 5,
                    textPaint
                )
            }
        }
        val priceStep = (upperValue - lowerValue) / 5f
        (0..4).forEach { i ->
            val textValue = lowerValue + priceStep * i;
            val text = if(upperValue in 0f..5f){
                (round(textValue*100) / 100).toString()
            }else if(upperValue in 5f..50f){
                (round(textValue*10) / 10).toString()
            }else if(upperValue in 50f..500f){
                round(textValue).toString()
            }else{
                (round(textValue / 10) * 10).toString()
            }
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    text,
                    30f,
                    size.height - spacing - i * size.height / 5,
                    textPaint
                )
            }
        }
        var lastX = 0f
        val strokPath = Path().apply {
            val height = size.height
            for(i in infos.indices){
                val info = infos[i]
                val nextInfo = infos.getOrNull(i + i) ?: infos.last()
                val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (i+1) * spacePerHour
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if(i == 0){
                    moveTo(x1, y1)
                }
                lastX = (x1 + x2) / 2f
                quadraticBezierTo(
                    x1, y1, (x1 + x2) / 2f, (y1 + y2) / 2f
                )
            }
        }
        val fillPath = android.graphics.Path(strokPath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }
        drawPath(
            path = fillPath,
            Brush.verticalGradient(
                colors = listOf(
                    transperantGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )
        drawPath(
            path = strokPath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}