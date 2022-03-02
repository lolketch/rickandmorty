package com.example.rickandmorty.di

import android.app.Application
import android.content.Context
import com.example.api.RemoteDataSource
import com.example.character_list.di.CharacterListDeps
import com.example.core.InternetConnection
import com.example.rickandmorty.di.module.NetworkModule
import dagger.*
import javax.inject.Scope

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent: CharacterListDeps {

    override val remoteDataSource: RemoteDataSource

    override val internetConnection: InternetConnection.Base

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