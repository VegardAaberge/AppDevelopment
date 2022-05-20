package com.plcoding.stockmarketapp.domain.models

import java.time.LocalDateTime

data class IntradayInfo(
    val date: LocalDateTime,
    val close: Double
)
