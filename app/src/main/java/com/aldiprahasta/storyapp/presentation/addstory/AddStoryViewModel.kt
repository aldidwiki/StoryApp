package com.aldiprahasta.storyapp.presentation.addstory

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldiprahasta.storyapp.domain.model.AddStoryDomainModel
import com.aldiprahasta.storyapp.domain.usecase.AddStory
import com.aldiprahasta.storyapp.utils.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(addStory: AddStory) : ViewModel() {
    private val addStoryData = MutableSharedFlow<Pair<MultipartBody.Part, RequestBody>>(1)
    fun setStoryData(storyData: Pair<MultipartBody.Part, RequestBody>) {
        this.addStoryData.tryEmit(storyData)
    }

    private val descriptionField = MutableStateFlow("")
    fun setDescriptionField(description: String) {
        descriptionField.value = description
    }

    private val imageUri = MutableStateFlow<Uri?>(null)
    fun setImageUri(imageUri: Uri?) {
        this.imageUri.value = imageUri
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val addStory: SharedFlow<UiState<AddStoryDomainModel>> = addStoryData.flatMapLatest { addStoryPair ->
        addStory(addStoryPair.first, addStoryPair.second)
    }.shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            1
    )

    val isValidButton: StateFlow<Boolean> = combine(imageUri, descriptionField) { uri, description ->
        uri != null && description.isNotBlank()
    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
    )
}