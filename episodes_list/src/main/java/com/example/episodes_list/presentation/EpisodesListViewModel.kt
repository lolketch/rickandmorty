package com.example.episodes_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.ViewState
import com.example.episodes_list.domain.FetchEpisodes
import javax.inject.Inject

internal class EpisodesListViewModel @Inject constructor(
    private val fetchEpisodes: FetchEpisodes
) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun fetchEpisodes(episodes: String) {
        fetchEpisodes.execute(
            queryParameters = mapOf("episodes" to episodes),
            onStart = {
                _viewState.value = ViewState.Loading
            },
            onSuccess = {
                _viewState.value = ViewState.Success(it)
            },
            onError = {
                _viewState.value = ViewState.Error(it.localizedMessage)
            }
        )
    }

    override fun onCleared() {
        fetchEpisodes.dispose()
        super.onCleared()
    }
}