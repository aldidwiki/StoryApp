package com.aldiprahasta.storyapp.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel
import com.aldiprahasta.storyapp.domain.usecase.wrapper.GetStoryWrapper
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MapsViewModel(getStoryWrapper: GetStoryWrapper) : ViewModel() {
    val getStoryLocation: StateFlow<UiState<List<StoryDomainModel>>> = getStoryWrapper
            .getStories()
            .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    UiState.Loading
            )
}