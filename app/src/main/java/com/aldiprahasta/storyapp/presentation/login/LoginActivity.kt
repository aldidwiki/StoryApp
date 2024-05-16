package com.aldiprahasta.storyapp.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aldiprahasta.storyapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            imgBack.setOnClickListener {
                finish()
            }
        }
    }
}