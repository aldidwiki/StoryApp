package com.aldiprahasta.storyapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel
import com.aldiprahasta.storyapp.domain.usecase.GetStories
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(getStories: GetStories) : ViewModel() {
    val stories: StateFlow<UiState<List<StoryDomainModel>>> = getStories()
            .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    UiState.Loading
            )
}