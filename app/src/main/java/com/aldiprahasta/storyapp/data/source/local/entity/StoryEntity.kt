package com.aldiprahasta.storyapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story_table")
data class StoryEntity(
        @PrimaryKey
        val id: String,
        val name: String,
        val description: String,
        val photoUrl: String,
        val createdAt: String
)
