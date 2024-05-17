package com.aldiprahasta.storyapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.aldiprahasta.storyapp.databinding.ActivityHomeBinding
import com.aldiprahasta.storyapp.presentation.addstory.AddStoryActivity
import com.aldiprahasta.storyapp.utils.doIfError
import com.aldiprahasta.storyapp.utils.doIfLoading
import com.aldiprahasta.storyapp.utils.doIfSuccess
import com.aldiprahasta.storyapp.utils.gone
import com.aldiprahasta.storyapp.utils.visible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        subscribeData()
    }

    private fun subscribeData() {
        lifecycleScope.launch {
            viewModel.stories.flowWithLifecycle(lifecycle).collect { state ->
                state.apply {
                    doIfLoading {
                        binding.pbHome.visible()
                    }

                    doIfError { _, errorMessage ->
                        binding.pbHome.gone()
                        Toast.makeText(this@HomeActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    doIfSuccess { data ->
                        binding.pbHome.gone()
                        println("debug: $data")
                    }
                }
            }
        }
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