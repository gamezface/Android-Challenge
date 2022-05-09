package com.gamezface.androidchallenge.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gamezface.androidchallenge.databinding.ActivityPinBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}