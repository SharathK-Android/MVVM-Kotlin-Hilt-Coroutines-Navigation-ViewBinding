package com.test.conversion.ui.main.api

import com.google.gson.Gson
import com.test.conversion.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder {

    private const val BASE_URL = "https://v6.exchangerate-api.com/v6/"

    val exchangeApi: ExchangeApi by lazy {
        buildRetrofit().create(ExchangeApi::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/${BuildConfig.API_KEY}/")
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpBuilder.addNetworkInterceptor(networkInterceptor())
        return okHttpBuilder.build()
    }

    private fun networkInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}