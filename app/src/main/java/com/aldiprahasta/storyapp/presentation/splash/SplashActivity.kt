package com.aldiprahasta.storyapp.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aldiprahasta.storyapp.databinding.ActivitySplashBinding
import com.aldiprahasta.storyapp.presentation.home.HomeActivity
import com.aldiprahasta.storyapp.presentation.welcomescreen.WelcomeActivity
import com.aldiprahasta.storyapp.utils.MyPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val myPreferences by inject<MyPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.Main) {
            val isLoggedIn = myPreferences.getTokenFromDataStore().isNotEmpty()
            val intent = if (isLoggedIn) {
                Intent(this@SplashActivity, HomeActivity::class.java)
            } else {
                Intent(this@SplashActivity, WelcomeActivity::class.java)
            }

            delay(1000)
            startActivity(intent)
            finish()
        }
    }
}