package com.example.currency.data

data class ExchangeRatesResponse(
    val base: String,
    val rates: Map<String, Double>
)
