package com.aldiprahasta.storyapp.presentation.welcomescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aldiprahasta.storyapp.databinding.ActivityWelcomeBinding
import com.aldiprahasta.storyapp.presentation.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}