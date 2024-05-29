package com.aldiprahasta.storyapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key_table")
data class RemoteKeys(
        @PrimaryKey val id: String,
        val prevKey: Int?,
        val nextKey: Int?
)
