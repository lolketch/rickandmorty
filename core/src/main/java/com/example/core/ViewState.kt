package com.example.core

sealed class ViewState {
    data class Success(val data: Any) : ViewState()
    data class Error(val message: String) : ViewState()
    object Loading : ViewState()
}
