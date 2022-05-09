package com.gamezface.pin.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gamezface.domain.ActivityConstants.HOME_ACTIVITY
import com.gamezface.pin.R
import com.gamezface.pin.databinding.FragmentPinBinding
import com.gamezface.presentation.viewmodels.pin.PinViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

@AndroidEntryPoint
class PinFragment : Fragment() {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private lateinit var binding: FragmentPinBinding

    private val viewModel: PinViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPinBinding.inflate(inflater, container, false).run {
        binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        setupBiometric()
        verifyBiometricAuth()
    }

    private fun initObservers() {
        viewModel.getPin().observe(viewLifecycleOwner) {
            binding.pinCodeNumPad.onPinChanged(it)
        }
        viewModel.getAuthentication().observe(viewLifecycleOwner) { authenticated ->
            if (authenticated) handleSuccessAuth()
            else handleErrorAuth()
        }
    }

    private fun initListeners() {
        binding.pinCodeNumPad.numPadListener = viewModel.numPadListener
    }

    private fun verifyBiometricAuth() {
        if (activity?.packageManager?.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) == true) {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun setupBiometric() {
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    handleSuccessAuth()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    handleErrorAuth()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.biometric_authentication))
            .setSubtitle(getString(R.string.biometric_authentication))
            .setNegativeButtonText(getString(R.string.insert_pin))
            .build()
    }

    private fun handleSuccessAuth() {
        startActivity(Intent(context, Class.forName(HOME_ACTIVITY)))
    }

    private fun handleErrorAuth() {
        Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show()
    }
}