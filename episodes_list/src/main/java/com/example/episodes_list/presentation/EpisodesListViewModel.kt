package com.example.episodes_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.episodes_list.EpisodesInfoViewState
import com.example.episodes_list.domain.FetchEpisodes
import javax.inject.Inject

internal class EpisodesListViewModel @Inject constructor(
    private val fetchEpisodes: FetchEpisodes
) : ViewModel() {
    private val _viewState = MutableLiveData<EpisodesInfoViewState>()
    val viewState: LiveData<EpisodesInfoViewState> = _viewState

    fun fetchEpisodes(episodes: String) {
        fetchEpisodes.execute(
            queryParameters = mapOf("episodes" to episodes),
            onStart = {
                _viewState.value = EpisodesInfoViewState.Loading
            },
            onSuccess = {
                _viewState.value = EpisodesInfoViewState.Success(it)
            },
            onError = {
                _viewState.value = EpisodesInfoViewState.Error(it.localizedMessage)
            },
            onFinished = {

            }
        )
    }

    override fun onCleared() {
        fetchEpisodes.dispose()
        super.onCleared()
    }
}