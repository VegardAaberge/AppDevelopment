package com.plcoding.stockmarketapp.data.mapper

import com.plcoding.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.plcoding.stockmarketapp.domain.models.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formater = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDate = LocalDateTime.parse(timestamp, formater)
    return IntradayInfo(
        date = localDate,
        close = close
    )
}

