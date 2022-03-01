package com.example.character_list

import androidx.paging.PagingData
import com.example.api.MyCharacter

sealed class CharactersListViewState {
    data class Success(val pagingData: PagingData<MyCharacter>) : CharactersListViewState()
    data class Error(val message: String) : CharactersListViewState()
    object Loading : CharactersListViewState()
}
