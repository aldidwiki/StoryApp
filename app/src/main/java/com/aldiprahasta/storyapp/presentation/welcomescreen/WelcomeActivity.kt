package com.aldiprahasta.storyapp.presentation.welcomescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aldiprahasta.storyapp.databinding.ActivityWelcomeBinding
import com.aldiprahasta.storyapp.presentation.login.LoginActivity
import com.aldiprahasta.storyapp.presentation.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                Intent(this@WelcomeActivity, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }

            btnSignup.setOnClickListener {
                Intent(this@WelcomeActivity, RegisterActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}