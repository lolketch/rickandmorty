package com.example.character_info.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.character_info.domain.FetchCharacterInfo
import com.example.core.ViewState
import javax.inject.Inject

internal class CharacterInfoViewModel @Inject constructor(
    private val fetchCharacterInfo: FetchCharacterInfo
) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun fetchCharacterInfo(id: Int) {
        fetchCharacterInfo.execute(
            queryParameters = mapOf("id" to id),
            onStart = {
                _viewState.value = ViewState.Loading
            },
            onSuccess = {
                _viewState.value = ViewState.Success(it)
            },
            onError = {
                _viewState.value = ViewState.Error(it.localizedMessage)
            },
            onFinished = {

            }
        )
    }

    override fun onCleared() {
        fetchCharacterInfo.dispose()
        super.onCleared()
    }
}