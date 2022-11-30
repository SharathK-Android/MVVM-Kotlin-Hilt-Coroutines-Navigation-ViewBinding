package com.test.conversion.ui.main.view

import com.test.conversion.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.test.conversion.databinding.FragmentExchangeCurrencyBinding
import com.test.conversion.ui.main.CONVERSION_FROM
import com.test.conversion.ui.main.CONVERSION_RATE
import com.test.conversion.ui.main.CONVERSION_TO
import com.test.conversion.ui.main.SPACE
import com.test.conversion.ui.main.viewmodel.ExchangeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyExchangeFragment : Fragment() {

    private lateinit var viewModel: ExchangeViewModel
    private lateinit var binding: FragmentExchangeCurrencyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ExchangeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangeCurrencyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrencies()
        initClickListener()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.currencyLiveData.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.Main) {
                val spinnerArrayAdapter = ArrayAdapter(
                    requireContext(),
                    androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                    it
                )
                spinnerArrayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
                binding.exchangeFromSpinner.adapter = spinnerArrayAdapter
                binding.exchangeToSpinner.adapter = spinnerArrayAdapter
            }
        }

        viewModel.conversionLiveData.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.Main) {
                binding.progressBar.visibility = View.GONE
                if (it.second.isNotEmpty()) {
                    val conversionFrom = "${binding.amount.text}$SPACE${it.second}"
                    val conversionTo = "${it.first.conversionResult}$SPACE${it.third}"

                    findNavController().navigate(
                        R.id.exchange_confirmation_fragment, bundleOf(
                            CONVERSION_FROM to conversionFrom, CONVERSION_TO to conversionTo,
                            CONVERSION_RATE to it.first.conversionRate
                        )
                    )
                }
            }
        }
    }

    private fun initClickListener() {
        with(binding) {
            btnCalculate.setOnClickListener {
                val enteredAmount = amount.text.toString()
                if (enteredAmount.isNotEmpty()) {
                    progressBar.visibility = View.VISIBLE
                    val currencyConversionFrom = exchangeFromSpinner.selectedItem.toString()
                    val currencyConversionTo = exchangeToSpinner.selectedItem.toString()
                    viewModel.getExchangeRate(
                        currencyConversionFrom,
                        currencyConversionTo,
                        enteredAmount.toDouble()
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearLiveData()
    }
}