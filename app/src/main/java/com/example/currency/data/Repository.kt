package com.example.currency.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {
    private val api = Retrofit.Builder()
        .baseUrl("https://open.er-api.com/v6/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun getExchangeRates(): Result<ExchangeRatesResponse> {
        return try {
            val response = api.getExchangeRates()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Virhe haettaessa valuuttoja"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
