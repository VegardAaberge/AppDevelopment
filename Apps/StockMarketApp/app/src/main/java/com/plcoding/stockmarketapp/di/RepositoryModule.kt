package com.plcoding.stockmarketapp.di

import com.plcoding.stockmarketapp.data.csv.CSVParser
import com.plcoding.stockmarketapp.data.csv.CompanyListingParser
import com.plcoding.stockmarketapp.data.csv.IntradayInfoParser
import com.plcoding.stockmarketapp.data.repository.StockRepositoryImpl
import com.plcoding.stockmarketapp.domain.models.CompanyListing
import com.plcoding.stockmarketapp.domain.models.IntradayInfo
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsCompanyListingsParser(
        companyListingsParser: CompanyListingParser
    ) : CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindsIntradayInfoParser(
        companyListingsParser: IntradayInfoParser
    ) : CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepository: StockRepositoryImpl
    ): StockRepository
}