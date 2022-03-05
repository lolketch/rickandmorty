package com.example.episodes_list.di

import com.example.core.di.FeatureScope
import com.example.episodes_list.domain.EpisodesListRepository
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {

    @FeatureScope
    @Binds
    fun bindRepository(episodeListRepositoryImpl: EpisodesListRepository.Base): EpisodesListRepository
}