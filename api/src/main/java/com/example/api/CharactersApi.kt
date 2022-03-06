package com.example.api

import com.example.api.models.CharacterDto
import com.example.api.models.Characters
import com.example.api.models.EpisodeDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersApi {
    @GET("character/")
    fun getCharacters(@Query("page") page: Int): Single<Characters>

    @GET("character/{id}")
    fun getCharacterInfo(@Path("id") id : Int): Single<CharacterDto>

    @GET("episode/{episode}")
    fun getEpisodesByCharacter(@Path("episode") episode : String): Single<List<EpisodeDto>>
}