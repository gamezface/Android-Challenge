package com.gamezface.uicommon.pin

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.gamezface.domain.pin.NumPadListener
import com.gamezface.uicommon.databinding.PinCodeNumPadBinding

class PinCodeNumPad(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {
    private val binding: PinCodeNumPadBinding
    var numPadListener: NumPadListener? = null

    init {
        LayoutInflater.from(context).also {
            binding = PinCodeNumPadBinding.inflate(it, this, true)
        }
        initView()
        initListeners()
    }

    private fun initView() {
        binding.viewPin1.isEnabled = false
        binding.viewPin2.isEnabled = false
        binding.viewPin3.isEnabled = false
        binding.viewPin4.isEnabled = false
    }

    private fun initListeners() {
        binding.buttonPassCode0.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.ZERO) }
        binding.buttonPassCode1.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.ONE) }
        binding.buttonPassCode2.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.TWO) }
        binding.buttonPassCode3.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.THREE) }
        binding.buttonPassCode4.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.FOUR) }
        binding.buttonPassCode5.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.FIVE) }
        binding.buttonPassCode6.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.SIX) }
        binding.buttonPassCode7.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.SEVEN) }
        binding.buttonPassCode8.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.EIGHT) }
        binding.buttonPassCode9.setOnClickListener { numPadListener?.onNumberClicked(NumPadConstants.NINE) }
        binding.buttonPassCodeBackspace.setOnClickListener { numPadListener?.onEraseClicked() }
    }

    fun onPinChanged(pin: String) {
        when (pin.length) {
            0 -> {
                binding.viewPin1.isEnabled = false
            }
            1 -> {
                binding.viewPin1.isEnabled = true
                binding.viewPin2.isEnabled = false
            }
            2 -> {
                binding.viewPin2.isEnabled = true
                binding.viewPin3.isEnabled = false
            }
            3 -> {
                binding.viewPin3.isEnabled = true
                binding.viewPin4.isEnabled = false
            }
            4 -> {
                binding.viewPin4.isEnabled = true
            }
            else -> return
        }
    }

    object NumPadConstants {
        const val ZERO = '0'
        const val ONE = '1'
        const val TWO = '2'
        const val THREE = '3'
        const val FOUR = '4'
        const val FIVE = '5'
        const val SIX = '6'
        const val SEVEN = '7'
        const val EIGHT = '8'
        const val NINE = '9'
    }
}