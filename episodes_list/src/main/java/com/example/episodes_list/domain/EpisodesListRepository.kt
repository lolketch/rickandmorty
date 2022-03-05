package com.example.episodes_list.domain

import com.example.api.EpisodeDto
import com.example.api.RemoteDataSource
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal interface EpisodesListRepository {
    fun fetchEpisodes(episodes: String): Single<List<EpisodeDto>>

    class Base @Inject constructor(private val remoteDataSource: RemoteDataSource) : EpisodesListRepository {
        override fun fetchEpisodes(episodes: String): Single<List<EpisodeDto>> {
            return remoteDataSource.fetchEpisodesByCharacter(episodes)
        }
    }
}