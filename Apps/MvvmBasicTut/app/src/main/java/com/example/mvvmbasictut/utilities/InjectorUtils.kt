package com.example.mvvmbasictut.utilities

import com.example.mvvmbasictut.data.FakeDatabase
import com.example.mvvmbasictut.data.FakeQuoteDao
import com.example.mvvmbasictut.data.QuoteRepository
import com.example.mvvmbasictut.ui.quotes.QuotesViewModelFactory

object InjectorUtils {
    fun provideQuotesViewModelFactory(): QuotesViewModelFactory {
        val quoteRepository = QuoteRepository.getInstance(FakeDatabase.getInstance().quoteDao)
        return QuotesViewModelFactory(quoteRepository)
    }
}