package com.example.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("air_date") val air_date: String,
    @SerialName("episode") val episode: String,
    @SerialName("characters") val characters: List<String>,
    @SerialName("url") val url: String,
    @SerialName("created") val created: String
)
