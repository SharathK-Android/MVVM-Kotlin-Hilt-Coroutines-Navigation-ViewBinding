package com.test.conversion.ui.main.model

import com.google.gson.annotations.SerializedName


data class Currencies(
    @SerializedName("supported_codes") var supportedCodes: List<List<String>>,
    var lastUpdateTime: Long
)

data class ConversionResult(
    @SerializedName("conversion_rate") val conversionRate: String,
    @SerializedName("conversion_result") val conversionResult: String
)