package com.example.character_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.rxjava3.cachedIn
import com.example.character_list.CharactersListViewState
import com.example.character_list.domain.FetchCharacters
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

internal class CharacterListViewModel @Inject constructor(
    private val fetchCharacters: FetchCharacters,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _viewState = MutableLiveData<CharactersListViewState>()
    val viewState: LiveData<CharactersListViewState> = _viewState

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        _viewState.value = CharactersListViewState.Loading
        compositeDisposable.addAll(
            fetchCharacters.getData()
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _viewState.value = CharactersListViewState.Success(it)
                }, {
                    _viewState.value = CharactersListViewState.Error(it.message.toString())
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
