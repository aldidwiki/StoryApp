package com.aldiprahasta.storyapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aldiprahasta.storyapp.data.source.local.dao.LocalDao
import com.aldiprahasta.storyapp.data.source.local.dao.RemoteKeyDao
import com.aldiprahasta.storyapp.data.source.local.entity.RemoteKeys
import com.aldiprahasta.storyapp.data.source.local.entity.StoryEntity

@Database(
        entities = [StoryEntity::class, RemoteKeys::class],
        version = 2
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localDao(): LocalDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        const val DB_NAME = "story_db"
    }
}