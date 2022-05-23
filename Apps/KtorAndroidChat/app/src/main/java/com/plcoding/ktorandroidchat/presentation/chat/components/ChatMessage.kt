package com.plcoding.ktorandroidchat.presentation.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.plcoding.ktorandroidchat.domain.model.Message

@Composable
fun ChatMessage(
    username: String?,
    message: Message,
    modifier: Modifier = Modifier
) {
    val isOwnMessage = message.username == username
    Box(
        modifier = modifier,
        contentAlignment = if(isOwnMessage) {
            Alignment.CenterEnd
        }
        else Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .width(200.dp)
                .drawBehind {
                    val cornerRadius = 10.dp.toPx()
                    val triangleHeight = 20.dp.toPx()

                    val triangleWidth =
                        if (isOwnMessage) -25.dp.toPx() else 25.dp.toPx()
                    val triangleStartX = if (isOwnMessage) size.width else 0f

                    val trianglePath = Path().apply {
                        moveTo(triangleStartX, size.height - cornerRadius)
                        lineTo(triangleStartX, size.height + triangleHeight)
                        lineTo(
                            triangleStartX + triangleWidth,
                            size.height - cornerRadius
                        )
                        close()
                    }
                    drawPath(
                        path = trianglePath,
                        color = if (isOwnMessage) Color.Green else Color.DarkGray
                    )
                }
                .background(
                    color = if (isOwnMessage) Color.Green else Color.DarkGray,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = message.username,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = message.text,
                color = Color.White
            )
            Text(
                text = message.formattedTime,
                color = Color.White,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}