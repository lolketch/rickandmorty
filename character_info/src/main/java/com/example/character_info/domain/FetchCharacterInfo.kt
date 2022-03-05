package com.example.character_info.domain

import com.example.core.SchedulerProvider
import com.example.core.base.UseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class FetchCharacterInfo @Inject constructor(
    private val repository: CharacterInfoRepository,
    schedulerProvider: SchedulerProvider
) : UseCase<Character>(schedulerProvider) {
    override fun buildUseCase(queryParameters: Map<String,Any>): Single<Character> {
        val id = queryParameters["id"] as Int
        return repository.fetchCharacterInfo(id).map { characterDto ->
            characterDto.toCharacter()
        }
    }
}