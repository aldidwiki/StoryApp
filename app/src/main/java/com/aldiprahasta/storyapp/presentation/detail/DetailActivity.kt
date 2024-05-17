package com.aldiprahasta.storyapp.presentation.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.aldiprahasta.storyapp.databinding.ActivityDetailBinding
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel
import com.aldiprahasta.storyapp.utils.parcelable

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
    }

    private fun setupData() {
        val storyModel = intent.parcelable<StoryDomainModel>(EXTRA_STORY_MODEL)
        binding.apply {
            imgStory.load(storyModel?.photoUrl) {
                crossfade(true)
                placeholder(ColorDrawable(Color.GRAY))
            }
            tvStoryTitle.text = storyModel?.name
            tvStoryBody.text = storyModel?.description
        }
    }

    companion object {
        private const val EXTRA_STORY_MODEL = "extra_story_model"

        fun newIntent(context: Context, storyModel: StoryDomainModel): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_STORY_MODEL, storyModel)
            }
        }
    }
}