package com.test.conversion.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.conversion.ui.main.EMPTY
import com.test.conversion.ui.main.model.ConversionResult
import com.test.conversion.ui.main.model.Currencies
import com.test.conversion.ui.main.repository.ExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(private val repository: ExchangeRepository) :
    ViewModel() {
    var currencyLiveData = MutableLiveData<Array<String>>()
    var conversionLiveData = MutableLiveData<Triple<ConversionResult, String, String>>()

    fun getCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            val currencyData = repository.getCurrencies()
            parseCurrencies(currencyData)
        }
    }

    private fun parseCurrencies(currencies: Currencies) {
        val array = arrayListOf<String>()
        for (i in currencies.supportedCodes.indices) {
            array.add(currencies.supportedCodes[i][0])
        }
        currencyLiveData.postValue(array.toTypedArray())
    }

    fun getExchangeRate(currencyFrom: String, currencyTo: String, amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val conversionData = repository.getExchangeRates(currencyFrom, currencyTo, amount)
            conversionLiveData.postValue(Triple(conversionData, currencyFrom, currencyTo))
        }
    }

    fun clearLiveData() {
        conversionLiveData.value = Triple(ConversionResult(EMPTY, EMPTY), EMPTY, EMPTY)
    }
}