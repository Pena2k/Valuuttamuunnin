package com.example.currency.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency.data.Repository
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    private val repository = Repository()

    var uiState by mutableStateOf(CurrencyUiState())
        private set

    init {
        fetchExchangeRates()
    }

    fun onAmountChange(amount: String) {
        uiState = uiState.copy(amount = amount)
        calculateConversion()
    }

    fun onFromCurrencyChange(currency: String) {
        uiState = uiState.copy(fromCurrency = currency)
        calculateConversion()
    }

    fun onToCurrencyChange(currency: String) {
        uiState = uiState.copy(toCurrency = currency)
        calculateConversion()
    }

    private fun fetchExchangeRates() {
        uiState = uiState.copy(isLoading = true, error = null)
        viewModelScope.launch {
            repository.getExchangeRates()
                .onSuccess { response ->
                    uiState = uiState.copy(
                        exchangeRates = response.rates,
                        isLoading = false
                    )
                    calculateConversion()
                }
                .onFailure { exception ->
                    uiState = uiState.copy(
                        error = exception.message ?: "Unknown error occurred",
                        isLoading = false
                    )
                }
        }
    }

    private fun calculateConversion() {
        val amount = uiState.amount.toDoubleOrNull() ?: 0.0
        val fromRate = uiState.exchangeRates[uiState.fromCurrency] ?: 1.0
        val toRate = uiState.exchangeRates[uiState.toCurrency] ?: 1.0

        val result = amount * (toRate / fromRate)
        uiState = uiState.copy(convertedAmount = "%.2f".format(result))
    }

    fun swapCurrencies() {
        uiState = uiState.copy(
            fromCurrency = uiState.toCurrency,
            toCurrency = uiState.fromCurrency
        )
        calculateConversion()
    }
}

data class CurrencyUiState(
    val amount: String = "",
    val fromCurrency: String = "EUR",
    val toCurrency: String = "USD",
    val convertedAmount: String = "",
    val exchangeRates: Map<String, Double> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)