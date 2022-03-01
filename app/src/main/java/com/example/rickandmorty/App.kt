package com.example.rickandmorty

import android.app.Application
import com.example.character_list.di.CharacterListDepsStore
import com.example.rickandmorty.di.AppComponent
import com.example.rickandmorty.di.DaggerAppComponent

class App: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        CharacterListDepsStore.deps = appComponent
    }
}