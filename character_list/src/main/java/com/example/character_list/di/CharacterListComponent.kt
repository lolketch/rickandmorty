package com.example.character_list.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.api.RemoteDataSource
import com.example.character_list.presentation.CharacterListFragment
import com.example.core.di.FeatureScope
import com.example.core.di.SchedulerModule
import dagger.Component
import kotlin.properties.Delegates

@FeatureScope
@Component(
    modules = [RepositoryModule::class, SchedulerModule::class],
    dependencies = [CharacterListDeps::class]
)
internal interface CharacterListComponent {
    fun inject(fragment: CharacterListFragment)

    @Component.Builder
    interface Builder {

        fun deps(deps: CharacterListDeps): Builder

        fun build(): CharacterListComponent
    }
}

interface CharacterListDeps {
    val remoteDataSource: RemoteDataSource
}


interface CharacterListDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: CharacterListDeps

    companion object : CharacterListDepsProvider by CharacterListDepsStore
}

object CharacterListDepsStore : CharacterListDepsProvider {

    override var deps: CharacterListDeps by Delegates.notNull()
}

internal class CharacterListComponentViewModel : ViewModel() {

    val newComponent =
        DaggerCharacterListComponent.builder().deps(CharacterListDepsProvider.deps).build()
}