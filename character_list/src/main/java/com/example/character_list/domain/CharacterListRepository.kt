package com.example.character_list.domain

import androidx.paging.PagingData
import com.example.api.MyCharacter
import io.reactivex.rxjava3.core.Flowable

internal interface CharacterListRepository {
    fun fetchCharacters(): Flowable<PagingData<MyCharacter>>
}