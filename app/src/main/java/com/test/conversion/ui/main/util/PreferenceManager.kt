package com.test.conversion.ui.main.util

import android.content.SharedPreferences
import com.google.gson.Gson
import com.test.conversion.ConversionApp
import com.test.conversion.ui.main.CURRENCIES
import com.test.conversion.ui.main.MY_PREF
import com.test.conversion.ui.main.model.Currencies

class PreferenceManager {

    private var pref: SharedPreferences =
        ConversionApp.instance.getSharedPreferences(MY_PREF, 0)

    var editor: SharedPreferences.Editor = pref.edit()


    fun storeCurrencies(currencies: Currencies) {
        val currencyData = Gson().toJson(currencies, Currencies::class.java)
        editor.putString(CURRENCIES, currencyData)
        editor.commit()
    }

    fun fetchCurrencies(): Currencies? {
        val currencyData = pref.getString(CURRENCIES, null)
        if (currencyData != null) {
            return Gson().fromJson(currencyData, Currencies::class.java)
        }
        return null
    }
}