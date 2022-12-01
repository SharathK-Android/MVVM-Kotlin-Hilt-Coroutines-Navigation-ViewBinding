package com.test.conversion.ui.main.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.test.conversion.ui.main.CURRENCIES
import com.test.conversion.ui.main.MY_PREF
import com.test.conversion.ui.main.model.Currencies
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceManager @Inject constructor(@ApplicationContext private val context: Context) {

    private var pref: SharedPreferences =
        context.getSharedPreferences(MY_PREF, 0)

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