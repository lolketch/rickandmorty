package com.example.episodes_list.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.episodes_list.EpisodesInfoViewState
import com.example.episodes_list.domain.FetchEpisodes
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

internal class EpisodesListViewModel @Inject constructor(
    private val fetchEpisodes: FetchEpisodes
) : ViewModel() {
    private val _viewState = MutableLiveData<EpisodesInfoViewState>()
    val viewState: LiveData<EpisodesInfoViewState> = _viewState

    fun fetchEpisodes(episodes: String) {
        fetchEpisodes.execute(
            data = episodes,
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
}