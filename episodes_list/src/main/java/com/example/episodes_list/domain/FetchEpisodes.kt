package com.example.episodes_list.domain

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class FetchEpisodes @Inject constructor(
    private val repository: EpisodesListRepository
) {
    fun getEpisodes(episodes: String): Single<List<Episode>> {
        return repository.fetchEpisodes(episodes).map { listOfEpisodesDto ->
            listOfEpisodesDto.map { episodeDto ->
                episodeDto.toEpisode()
            }
        }
    }
}