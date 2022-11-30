package com.test.conversion.ui.main.api

import com.test.conversion.ui.main.model.ConversionResult
import com.test.conversion.ui.main.model.Currencies
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeApi {

    @GET("codes")
   suspend fun getCurrencies(): Currencies

    @GET("pair/{currency_from}/{currency_to}/{amount}")
    suspend fun getExchangeRates(
        @Path("currency_from") currencyFrom: String,
        @Path("currency_to") currencyTo: String,
        @Path("amount") amount: Double,
    ): ConversionResult
}