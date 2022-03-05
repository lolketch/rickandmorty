package com.example.episodes_list.domain

import com.example.core.SchedulerProvider
import com.example.core.base.UseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

internal class FetchEpisodes @Inject constructor(
    private val repository: EpisodesListRepository,
    schedulerProvider: SchedulerProvider
) : UseCase<List<Episode>, String>(schedulerProvider) {

    override fun buildUseCase(data: String): Single<List<Episode>> {
        return repository.fetchEpisodes(data).map { listOfEpisodesDto ->
            listOfEpisodesDto.map { episodeDto ->
                episodeDto.toEpisode()
            }
        }
    }
}