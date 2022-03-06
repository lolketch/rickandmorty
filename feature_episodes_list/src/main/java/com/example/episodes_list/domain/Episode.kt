package com.example.episodes_list.domain

import com.example.api.models.EpisodeDto

data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)

fun EpisodeDto.toEpisode(): Episode {
    return Episode(
        id = id,
        name = name,
        air_date = air_date,
        episode = episode,
        characters = characters,
        url = url,
        created = created
    )
}
