package com.example.character_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava3.cachedIn
import com.example.core.ViewState
import com.example.character_list.domain.FetchCharacters
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

internal class CharacterListViewModel @Inject constructor(
    private val fetchCharacters: FetchCharacters,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        _viewState.value = ViewState.Loading
        compositeDisposable.addAll(
            fetchCharacters.getData()
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _viewState.value = ViewState.Success(it)
                }, {
                    _viewState.value = ViewState.Error(it.message.toString())
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
