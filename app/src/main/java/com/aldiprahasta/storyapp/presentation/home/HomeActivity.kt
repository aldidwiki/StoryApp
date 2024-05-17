package com.aldiprahasta.storyapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.aldiprahasta.storyapp.R
import com.aldiprahasta.storyapp.databinding.ActivityHomeBinding
import com.aldiprahasta.storyapp.presentation.addstory.AddStoryActivity
import com.aldiprahasta.storyapp.presentation.detail.DetailActivity
import com.aldiprahasta.storyapp.presentation.welcomescreen.WelcomeActivity
import com.aldiprahasta.storyapp.utils.MyPreferences
import com.aldiprahasta.storyapp.utils.doIfError
import com.aldiprahasta.storyapp.utils.doIfLoading
import com.aldiprahasta.storyapp.utils.doIfSuccess
import com.aldiprahasta.storyapp.utils.gone
import com.aldiprahasta.storyapp.utils.visible
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
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            lifecycleScope.launch(Dispatchers.Main) {
                myPreferences.deleteTokenFromDataStore()
                Intent(this@HomeActivity, WelcomeActivity::class.java).also {
                    startActivity(it)
                    finishAffinity()
                }
            }
        }
        return true
    }

    private fun setupRecyclerView() {
        binding.rvStory.adapter = storyAdapter
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
                        storyAdapter.submitList(data)
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