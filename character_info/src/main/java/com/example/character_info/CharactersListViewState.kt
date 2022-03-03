package com.example.character_info

import com.example.character_info.domain.Character

sealed class CharactersInfoViewState {
    data class Success(val character: Character) : CharactersInfoViewState()
    data class Error(val message: String) : CharactersInfoViewState()
    object Loading : CharactersInfoViewState()
}
