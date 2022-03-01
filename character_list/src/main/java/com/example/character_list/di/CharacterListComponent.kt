package com.example.character_list.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.api.RemoteDataSource
import com.example.character_list.presentation.CharacterListFragment
import com.example.core.FeatureScope
import dagger.Component
import kotlin.properties.Delegates

@FeatureScope
@Component(
    modules = [],
    dependencies = [CharacterListDeps::class]
)
internal interface CharacterListComponent {
    fun inject(fragment: CharacterListFragment)

    @Component.Builder
    interface Builder {

        fun deps(departmentDeps: CharacterListDeps): Builder

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