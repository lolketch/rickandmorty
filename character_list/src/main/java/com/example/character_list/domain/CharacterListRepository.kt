package com.example.character_list.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.example.api.CharacterDto
import com.example.api.RemoteDataSource
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

internal interface CharacterListRepository {
    fun fetchCharacters(): Observable<PagingData<CharacterDto>>

    class Base @Inject constructor(private val remoteDataSource: RemoteDataSource) :
        CharacterListRepository {
        override fun fetchCharacters(): Observable<PagingData<CharacterDto>> {
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false,
                    prefetchDistance = 5
                ),
                pagingSourceFactory = { remoteDataSource.fetchUsers() }
            ).observable
        }
    }
}