package com.example.character_info.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.character_info.CharactersInfoViewState
import com.example.character_info.domain.FetchCharacterInfo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

internal class CharacterInfoViewModel @Inject constructor(
    private val fetchCharacterInfo: FetchCharacterInfo
) : ViewModel() {
    private val _viewState = MutableLiveData<CharactersInfoViewState>()
    val viewState: LiveData<CharactersInfoViewState> = _viewState

    fun fetchCharacterInfo(id: Int) {
        fetchCharacterInfo.execute(
            id = id,
            onStart = {
                _viewState.value = CharactersInfoViewState.Loading
            },
            onSuccess = {
                _viewState.value = CharactersInfoViewState.Success(it)
            },
            onError = {
                _viewState.value = CharactersInfoViewState.Error(it.localizedMessage)
            },
            onFinished = {

            }
        )
    }
}