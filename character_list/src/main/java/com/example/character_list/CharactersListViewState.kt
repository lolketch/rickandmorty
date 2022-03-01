package com.example.character_list

import androidx.paging.PagingData
import com.example.character_list.domain.Character

sealed class CharactersListViewState {
    data class Success(val pagingData: PagingData<Character>) : CharactersListViewState()
    data class Error(val message: String) : CharactersListViewState()
    object Loading : CharactersListViewState()
}
