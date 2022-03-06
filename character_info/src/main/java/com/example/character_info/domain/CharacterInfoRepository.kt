package com.example.character_info.domain

import com.example.api.models.CharacterDto
import com.example.api.RemoteDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface CharacterInfoRepository {

    fun fetchCharacterInfo(id: Int): Single<CharacterDto>

    class Base @Inject constructor(private val remoteDataSource: RemoteDataSource) :
        CharacterInfoRepository {
        override fun fetchCharacterInfo(id: Int): Single<CharacterDto> {
            return remoteDataSource.fetchCharacterInfo(id)
        }
    }
}