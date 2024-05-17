package com.aldiprahasta.storyapp.presentation.welcomescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aldiprahasta.storyapp.databinding.ActivityWelcomeBinding
import com.aldiprahasta.storyapp.presentation.home.HomeActivity
import com.aldiprahasta.storyapp.presentation.login.LoginActivity
import com.aldiprahasta.storyapp.presentation.register.RegisterActivity
import com.aldiprahasta.storyapp.utils.MyPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val myPreferences by inject<MyPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        lifecycleScope.launch {
            val isLoggedIn = myPreferences.getTokenFromDataStore().isNotEmpty()

            withContext(Dispatchers.Main) {
                binding.btnLogin.setOnClickListener {
                    val intent = if (isLoggedIn) {
                        Intent(this@WelcomeActivity, HomeActivity::class.java).also {
                            finish()
                        }
                    } else {
                        Intent(this@WelcomeActivity, LoginActivity::class.java)
                    }

                    startActivity(intent)
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