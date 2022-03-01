package com.example.api

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val retrofitApi: CharactersApi) {

    fun fetchUsers() = GetCharactersRxPagingSource(retrofitApi)
}

class GetCharactersRxPagingSource(private val retrofitApi: CharactersApi) : RxPagingSource<Int, CharacterDto>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, CharacterDto>> {
        val page: Int = params.key ?: 1

        return retrofitApi.getCharacters(page = page)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(data = it, page = page) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: Characters, page: Int): LoadResult<Int, CharacterDto> {
        return LoadResult.Page(
            data = data.results,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (page == data.info.pages) null else page + 1
        )
    }

}