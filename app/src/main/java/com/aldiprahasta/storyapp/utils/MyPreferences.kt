package com.aldiprahasta.storyapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class MyPreferences(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
    private val tokenKey = stringPreferencesKey("story_app_token")

    suspend fun writeTokenToDataStore(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    suspend fun getTokenFromDataStore(): String {
        val preferences = context.dataStore.data.first()
        return preferences[tokenKey] ?: ""
    }

    suspend fun deleteTokenFromDataStore() {
        context.dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "story_app_preferences"
    }
}