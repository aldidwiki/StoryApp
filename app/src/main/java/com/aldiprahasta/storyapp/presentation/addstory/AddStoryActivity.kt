package com.aldiprahasta.storyapp.presentation.addstory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aldiprahasta.storyapp.databinding.ActivityAddStoryBinding

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}