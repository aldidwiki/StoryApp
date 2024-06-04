package com.aldiprahasta.storyapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aldiprahasta.storyapp.domain.model.StoryDomainModel
import com.aldiprahasta.storyapp.domain.usecase.wrapper.GetStoryWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(getStoryWrapper: GetStoryWrapper) : ViewModel() {
    private val isFetchAgain = MutableStateFlow(false)
    fun setFetchAgain(isFetchAgain: Boolean) {
        this.isFetchAgain.value = isFetchAgain
    }

    val storiesWithPaging: StateFlow<PagingData<StoryDomainModel>> = isFetchAgain.flatMapLatest {
        getStoryWrapper.getStoriesWithPaging()
    }
            .cachedIn(viewModelScope)
            .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    PagingData.empty()
            )
}