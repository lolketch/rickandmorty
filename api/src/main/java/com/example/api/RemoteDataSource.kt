package com.example.api

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val retrofitApi: CharactersApi,
    private val getCharactersRxPagingSource: GetCharactersRxPagingSource
) {
    fun fetchCharacters() = getCharactersRxPagingSource

    fun fetchCharacterInfo(id: Int) = retrofitApi.getCharacterInfo(id)

    fun fetchEpisodesByCharacter(episodes: String) = retrofitApi.getEpisodesByCharacter(episodes)
}