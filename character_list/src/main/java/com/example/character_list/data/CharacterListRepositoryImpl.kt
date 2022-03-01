package com.example.character_list.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.api.MyCharacter
import com.example.api.RemoteDataSource
import com.example.character_list.domain.CharacterListRepository
import io.reactivex.Flowable
import javax.inject.Inject

internal class CharacterListRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    CharacterListRepository {
    override fun fetchCharacters(): Flowable<PagingData<MyCharacter>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { remoteDataSource.getUsers() }
        ).flowable
    }
}