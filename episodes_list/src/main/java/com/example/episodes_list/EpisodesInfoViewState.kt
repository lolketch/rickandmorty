package com.example.episodes_list

import com.example.episodes_list.domain.Episode

sealed class EpisodesInfoViewState {
    data class Success(val episodes: List<Episode>) : EpisodesInfoViewState()
    data class Error(val message: String) : EpisodesInfoViewState()
    object Loading : EpisodesInfoViewState()
}
