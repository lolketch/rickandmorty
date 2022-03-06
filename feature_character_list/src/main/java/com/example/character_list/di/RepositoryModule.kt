package com.example.character_list.di

import com.example.character_list.domain.CharacterListRepository
import com.example.core.di.FeatureScope
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {

    @FeatureScope
    @Binds
    fun bindRepository(characterListRepositoryImpl: CharacterListRepository.Base): CharacterListRepository
}