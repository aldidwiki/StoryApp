package com.aldiprahasta.storyapp.domain.usecase.wrapper

import com.aldiprahasta.storyapp.domain.usecase.GetStories
import com.aldiprahasta.storyapp.domain.usecase.GetStoriesWithPaging

data class GetStoryWrapper(
        val getStories: GetStories,
        val getStoriesWithPaging: GetStoriesWithPaging
)
