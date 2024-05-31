package com.aldiprahasta.storyapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.aldiprahasta.storyapp.R
import com.aldiprahasta.storyapp.databinding.ActivityHomeBinding
import com.aldiprahasta.storyapp.presentation.addstory.AddStoryActivity
import com.aldiprahasta.storyapp.presentation.detail.DetailActivity
import com.aldiprahasta.storyapp.presentation.map.MapsActivity
import com.aldiprahasta.storyapp.presentation.welcomescreen.WelcomeActivity
import com.aldiprahasta.storyapp.utils.MyPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private val storyAdapter: StoryAdapter by lazy { StoryAdapter() }
    private val myPreferences by inject<MyPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarHome)

        setupListener()
        setupRecyclerView()
        subscribeData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_story, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                lifecycleScope.launch(Dispatchers.Main) {
                    myPreferences.deleteTokenFromDataStore()
                    Intent(this@HomeActivity, WelcomeActivity::class.java).also {
                        startActivity(it)
                        finishAffinity()
                    }
                }
            }

            R.id.action_map -> {
                Intent(this, MapsActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return true
    }

    private fun setupRecyclerView() {
        binding.rvStory.adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
        )
        storyAdapter.setOnItemClickCallback { model, view ->
            val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@HomeActivity,
                    Pair(view.imgStory, "image"),
                    Pair(view.tvStoryTitle, "title"),
                    Pair(view.tvStoryBody, "description")
            )
            DetailActivity.newIntent(this, model).also {
                startActivity(it, optionsCompat.toBundle())
            }
        }
    }

    private fun subscribeData() {
        lifecycleScope.launch {
            viewModel.storiesWithPaging.flowWithLifecycle(lifecycle).collect {
                storyAdapter.submitData(it)
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