package com.aldiprahasta.storyapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryDomainModel(
        val id: String,
        val name: String,
        val description: String,
        val photoUrl: String,
        val createdAt: String,
        val lat: Double? = null,
        val lng: Double? = null
) : Parcelable
