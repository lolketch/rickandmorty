package com.example.character_list.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.example.core.SchedulerProvider
import com.example.core.base.UseCase
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

internal class FetchCharacters @Inject constructor(
    private val repository: CharacterListRepository,
    schedulerProvider: SchedulerProvider
) : UseCase<PagingData<Character>>(schedulerProvider) {
    override fun buildUseCase(): Observable<PagingData<Character>> {
        return repository.fetchCharacters().map { pagingData ->
            pagingData.map { characterDto ->
                characterDto.toCharacter()
            }
        }
    }
}

//interface CharacterListUseCase {
//    interface FetchEpisodes { //todo(1:49:00)
//        fun getEpisodes()
//    }
//
//    interface FetchPlanets {
//        fun getPlanets()
//    }
//
//    interface FetchCharacters<T> : UseCaseTest<T>
//
//    class Base @Inject constructor(
//        private val repository: CharacterListRepository,
//        override val schedulerProvider: SchedulerProvider,
//
//        ) : CharacterListUseCase.FetchCharacters<PagingData<Character>> {
//
//        override val compositeDisposable = CompositeDisposable()
//
//        override fun buildUseCase(): Observable<PagingData<Character>> {
//            TODO("Not yet implemented")
//        }
//
//    }
//}