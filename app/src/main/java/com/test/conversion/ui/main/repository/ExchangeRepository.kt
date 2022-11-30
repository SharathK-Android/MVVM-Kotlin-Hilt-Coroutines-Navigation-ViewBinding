package com.test.conversion.ui.main.repository

import com.test.conversion.ui.main.api.ApiBuilder.exchangeApi
import com.test.conversion.ui.main.model.ConversionResult
import com.test.conversion.ui.main.model.Currencies
import com.test.conversion.ui.main.util.PreferenceManager
import java.util.concurrent.TimeUnit

class ExchangeRepository {

    private val preferenceManager = PreferenceManager()

    suspend fun getCurrencies(): Currencies {
        var currencyData: Currencies? = preferenceManager.fetchCurrencies()
        val lastUpdatedTime = currencyData?.lastUpdateTime ?: 0L
        val timeDifference =
            TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis() - lastUpdatedTime)
        return if (timeDifference < 5 && currencyData != null) {
            currencyData
        } else {
            currencyData = exchangeApi.getCurrencies()
            currencyData.lastUpdateTime = System.currentTimeMillis()
            currencyData.supportedCodes = currencyData.supportedCodes.take(10)
            preferenceManager.storeCurrencies(currencyData)
            currencyData
        }
    }

    suspend fun getExchangeRates(
        currencyFrom: String,
        currencyTo: String,
        amount: Double
    ): ConversionResult {
        return exchangeApi.getExchangeRates(currencyFrom, currencyTo, amount)
    }
}