package com.example.currency.data

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("latest/EUR")
    suspend fun getExchangeRates(): Response<ExchangeRatesResponse>
}
