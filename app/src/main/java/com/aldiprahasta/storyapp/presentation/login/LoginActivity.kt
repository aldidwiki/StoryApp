package com.aldiprahasta.storyapp.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aldiprahasta.storyapp.databinding.ActivityLoginBinding
import com.aldiprahasta.storyapp.presentation.home.HomeActivity

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

            btnLogin.setOnClickListener {
                Intent(this@LoginActivity, HomeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}