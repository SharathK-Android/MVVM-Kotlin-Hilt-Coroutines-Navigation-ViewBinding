package com.test.conversion.ui.main.view

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.test.conversion.R
import com.test.conversion.databinding.FragmentExchangeConfirmationBinding
import com.test.conversion.ui.main.CONVERSION_FROM
import com.test.conversion.ui.main.CONVERSION_RATE
import com.test.conversion.ui.main.CONVERSION_TO

class ExchangeConfirmationFragment : Fragment() {

    private lateinit var binding: FragmentExchangeConfirmationBinding
    private var conversionFrom: String? = null
    private var conversionTo: String? = null
    private var conversionRate: String? = null
    private var alertDialog: AlertDialog? = null
    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangeConfirmationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            conversionFrom = getString(CONVERSION_FROM)
            conversionTo = getString(CONVERSION_TO)
            conversionRate = getString(CONVERSION_RATE)
        }
        with(binding) {
            currencyFrom.text = conversionFrom
            currencyTo.text = conversionTo
            btnConvert.setOnClickListener { displayConfirmationPopup() }
        }
        startCountDown()
    }

    private fun displayConfirmationPopup() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.approval_required))
            ?.setMessage(getString(R.string.approval_description, conversionFrom, conversionTo))
            ?.setCancelable(false)
            ?.setPositiveButton(getString(R.string.cancel)) { dialogInterface, _ ->
                dialogInterface?.dismiss()
            }?.setNegativeButton(getString(R.string.approve)) { _, _ ->
                findNavController().navigate(
                    R.id.action_navigate_success_screen, bundleOf(
                        CONVERSION_TO to conversionTo,
                        CONVERSION_RATE to conversionRate
                    )
                )
            }

        alertDialog = builder.create()
        alertDialog?.show()
    }

    private fun startCountDown() {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val time = millisUntilFinished / 1000
                binding.timer.text = getString(R.string.timer_in_sec, time)
            }

            override fun onFinish() {
                alertDialog?.dismiss()
                findNavController().popBackStack()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }
}