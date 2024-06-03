package com.aldiprahasta.storyapp

import com.aldiprahasta.storyapp.data.source.local.entity.StoryEntity


object DataDummy {
    fun generateDummyStories(): List<StoryEntity> {
        val items: MutableList<StoryEntity> = mutableListOf()
        for (i in 0..100) {
            val story = StoryEntity(
                    photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                    createdAt = "2022-01-08T06:34:18.598Z",
                    name = "Dimas $i",
                    description = "Lorem Ipsum",
                    id = "story-FvU4u0Vp2S3PMsFg_$i",
            )
            items.add(story)
        }
        return items
    }
}