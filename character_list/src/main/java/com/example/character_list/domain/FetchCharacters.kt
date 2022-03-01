package com.example.character_list.domain

import androidx.paging.PagingData
import com.example.api.MyCharacter
import com.example.core.SchedulerProvider
import com.example.core.base.UseCase
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

internal class FetchCharacters @Inject constructor(
    private val repository: CharacterListRepository,
    schedulerProvider: SchedulerProvider
) : UseCase<PagingData<MyCharacter>>(schedulerProvider) {
    override fun buildUseCaseFlowable(): Flowable<PagingData<MyCharacter>> {
        return repository.fetchCharacters()
    }

}