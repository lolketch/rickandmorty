package com.example.core.di

import com.example.core.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
interface SchedulerModule {

    @FeatureScope
    @Binds
    fun bindScheduler(appSchedulerProvider: SchedulerProvider.Base): SchedulerProvider
}