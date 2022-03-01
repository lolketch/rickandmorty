package com.example.character_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.character_list.CharactersListViewState
import com.example.character_list.domain.FetchCharacters
import javax.inject.Inject

internal class CharacterListViewModel @Inject constructor(
    private val fetchCharacters: FetchCharacters
) : ViewModel() {
    private val _viewState = MutableLiveData<CharactersListViewState>()
    val viewState: LiveData<CharactersListViewState> = _viewState

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        fetchCharacters.execute(
            onStart = {
                _viewState.value = CharactersListViewState.Loading
            },
            onSuccess = {
                _viewState.value = CharactersListViewState.Success(it)
            },
            onError = {
                _viewState.value = CharactersListViewState.Error(it.message.toString())
            }
        )
    }

    override fun onCleared() {
        fetchCharacters.dispose()
        super.onCleared()
    }
}