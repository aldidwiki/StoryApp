package com.aldiprahasta.storyapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel
import com.aldiprahasta.storyapp.domain.usecase.wrapper.GetStoryWrapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(getStoryWrapper: GetStoryWrapper) : ViewModel() {
    val storiesWithPaging: StateFlow<PagingData<StoryDomainModel>> = getStoryWrapper.getStoriesWithPaging()
            .cachedIn(viewModelScope)
            .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    PagingData.empty()
            )
}