package com.example.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Results(
    @SerialName("info") val info: Info,
    @SerialName("results") val results: List<MyCharacter>
)

@Serializable
data class Info(
    @SerialName("count") var count: Int,
    @SerialName("pages") var pages: Int,
    @SerialName("next") var next: String,
    @SerialName("prev") var prev: String
)

@Serializable
data class MyCharacter(
    @SerialName("id") var id: Int,
    @SerialName("name") var name: String,
    @SerialName("status") var status: String,
    @SerialName("species") var species: String,
    @SerialName("type") var type: String,
    @SerialName("gender") var gender: String,
    @SerialName("image") var image: String,
    @SerialName("episode") var episode: List<String>,
    @SerialName("url") var url: String,
    @SerialName("created") var created: String
)
