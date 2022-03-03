package com.example.character_info.di

import com.example.character_info.domain.CharacterInfoRepository
import com.example.core.di.FeatureScope
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryModule {

    @FeatureScope
    @Binds
    fun bindRepository(characterInfoRepository: CharacterInfoRepository.Base): CharacterInfoRepository
}