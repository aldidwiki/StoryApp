package com.aldiprahasta.storyapp.utils

sealed class UiState<out R> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(
            val throwable: Throwable,
            val errorMessage: String? = null
    ) : UiState<Nothing>()

    data object Loading : UiState<Nothing>()
}

inline fun <reified T> UiState<T>.doIfSuccess(callback: (data: T) -> Unit) {
    if (this is UiState.Success) callback(data)
}

inline fun <reified T> UiState<T>.doIfError(callback: (throwable: Throwable, errorMessage: String?) -> Unit) {
    if (this is UiState.Error) callback(throwable, errorMessage)
}

inline fun <reified T> UiState<T>.doIfLoading(callback: () -> Unit) {
    if (this is UiState.Loading) callback()
}
