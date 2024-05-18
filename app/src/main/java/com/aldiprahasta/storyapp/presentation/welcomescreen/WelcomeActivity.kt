package com.aldiprahasta.storyapp.presentation.welcomescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aldiprahasta.storyapp.databinding.ActivityWelcomeBinding
import com.aldiprahasta.storyapp.presentation.login.LoginActivity
import com.aldiprahasta.storyapp.presentation.register.RegisterActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                binding.btnLogin.setOnClickListener {
                    Intent(this@WelcomeActivity, LoginActivity::class.java).also { intent ->
                        startActivity(intent)
                    }
                }

                binding.btnSignup.setOnClickListener {
                    Intent(this@WelcomeActivity, RegisterActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
        }
    }
}