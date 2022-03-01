package com.example.rickandmorty.di

import android.app.Application
import com.example.character_list.di.CharacterListDeps
import com.example.rickandmorty.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent: CharacterListDeps {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}

@Module(includes = [NetworkModule::class])
class AppModule

@Scope
annotation class AppScope