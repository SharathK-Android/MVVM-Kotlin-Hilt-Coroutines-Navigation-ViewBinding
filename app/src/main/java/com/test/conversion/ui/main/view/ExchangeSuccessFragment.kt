package com.test.conversion.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.conversion.R
import com.test.conversion.databinding.FragmentExchangeSuccessBinding
import com.test.conversion.ui.main.CONVERSION_RATE
import com.test.conversion.ui.main.CONVERSION_TO

class ExchangeSuccessFragment : Fragment() {

    lateinit var binding: FragmentExchangeSuccessBinding
    private var conversionTo: String? = null
    private var conversionRate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangeSuccessBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            conversionTo = getString(CONVERSION_TO)
            conversionRate = getString(CONVERSION_RATE)
        }

        binding.currencyTo.text = getString(R.string.conversion_success_message, conversionTo)
        binding.conversionRate.text = getString(R.string.conversion_rate, conversionRate)
    }
}