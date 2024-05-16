package com.aldiprahasta.storyapp.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aldiprahasta.storyapp.databinding.ActivityHomeBinding
import com.aldiprahasta.storyapp.presentation.addstory.AddStoryActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
    }

    private fun setupListener() {
        binding.apply {
            fbAddStory.setOnClickListener {
                Intent(this@HomeActivity, AddStoryActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}