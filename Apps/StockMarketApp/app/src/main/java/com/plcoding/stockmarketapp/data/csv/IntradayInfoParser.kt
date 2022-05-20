package com.plcoding.stockmarketapp.data.csv

import com.opencsv.CSVReader
import com.plcoding.stockmarketapp.data.mapper.toIntradayInfo
import com.plcoding.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.plcoding.stockmarketapp.domain.models.CompanyListing
import com.plcoding.stockmarketapp.domain.models.IntradayInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.DayOfWeek
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor(): CSVParser <IntradayInfo> {

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(
                        timestamp = timestamp,
                        close = close.toDouble()
                    )
                    dto.toIntradayInfo()
                }
                .filter {
                    val subtractDay = if(LocalDateTime.now().dayOfWeek == DayOfWeek.MONDAY){
                        3L
                    }else if(LocalDateTime.now().dayOfWeek == DayOfWeek.SUNDAY){
                        2L
                    }else 1L
                    it.date.dayOfMonth == LocalDateTime.now().minusDays(subtractDay).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}