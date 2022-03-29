package com.example.mvvmbasictut.ui.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvmbasictut.data.Quote
import com.example.mvvmbasictut.data.QuoteRepository

class QuotesViewModel(private val quotesRepository: QuoteRepository)
    : ViewModel() {

    fun getQuotes() = quotesRepository.getQuote()

    fun addQuote(quote: Quote) = quotesRepository.addQuote(quote)
}