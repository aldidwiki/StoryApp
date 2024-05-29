package com.aldiprahasta.storyapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aldiprahasta.storyapp.data.source.local.entity.StoryEntity

@Database(
        entities = [StoryEntity::class],
        version = 1
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDao

    companion object {
        const val DB_NAME = "story_db"
    }
}