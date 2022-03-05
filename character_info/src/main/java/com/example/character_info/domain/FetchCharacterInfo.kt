package com.example.character_info.domain

import android.util.Log
import com.example.core.SchedulerProvider
import com.example.core.base.UseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class FetchCharacterInfo @Inject constructor(
    private val repository: CharacterInfoRepository,
    schedulerProvider: SchedulerProvider
) : UseCase<Character, Int>(schedulerProvider) {
    override fun buildUseCase(id: Int): Single<Character> {
        return repository.fetchCharacterInfo(id).map { characterDto ->
            characterDto.toCharacter()
        }
    }
}