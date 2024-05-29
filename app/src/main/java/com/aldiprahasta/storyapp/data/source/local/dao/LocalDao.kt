package com.aldiprahasta.storyapp.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aldiprahasta.storyapp.data.source.local.entity.StoryEntity

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<StoryEntity>)

    @Query("SELECT * FROM story_table")
    fun getAllStory(): PagingSource<Int, StoryEntity>

    @Query("DELETE FROM story_table")
    suspend fun deleteAllStory()
}