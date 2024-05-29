package com.aldiprahasta.storyapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aldiprahasta.storyapp.data.source.local.entity.RemoteKeys

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_key_table WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeys?

    @Query("DELETE FROM remote_key_table")
    suspend fun deleteRemoteKeys()
}