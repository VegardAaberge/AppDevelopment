package com.plcoding.stockmarketapp.domain.repository

import com.plcoding.stockmarketapp.domain.models.CompanyInfo
import com.plcoding.stockmarketapp.domain.models.CompanyListing
import com.plcoding.stockmarketapp.domain.models.IntradayInfo
import com.plcoding.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        system: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}