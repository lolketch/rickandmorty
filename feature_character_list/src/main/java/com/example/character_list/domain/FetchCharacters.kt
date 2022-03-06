package com.example.character_list.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.SchedulerProvider
import com.example.core.base.UseCase
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

internal class FetchCharacters @Inject constructor(
    private val repository: CharacterListRepository
) {
    fun getData(): Observable<PagingData<Character>> {
        return repository.fetchCharacters().map { pagingData ->
            pagingData.map { characterDto ->
                characterDto.toCharacter()
            }
        }
    }
}