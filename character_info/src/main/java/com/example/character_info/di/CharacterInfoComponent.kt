package com.example.character_info.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.api.RemoteDataSource
import com.example.character_info.presentation.CharacterInfoFragment
import com.example.core.di.FeatureScope
import com.example.core.di.SchedulerModule
import dagger.Component
import kotlin.properties.Delegates

@FeatureScope
@Component(
    modules = [RepositoryModule::class, SchedulerModule::class],
    dependencies = [CharacterInfoDeps::class]
)
internal interface CharacterInfoComponent {

    fun inject(fragment: CharacterInfoFragment)

    @Component.Builder
    interface Builder {

        fun deps(deps: CharacterInfoDeps): Builder

        fun build(): CharacterInfoComponent
    }
}

interface CharacterInfoDeps {
    val remoteDataSource: RemoteDataSource

    val internetConnection: InternetConnection
}

interface CharacterInfoDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: CharacterInfoDeps

    companion object: CharacterInfoDepsProvider by CharacterInfoDepsStore
}

object CharacterInfoDepsStore : CharacterInfoDepsProvider {
    override var deps: CharacterInfoDeps by Delegates.notNull()
}

internal class CharacterInfoComponentViewModel : ViewModel() {

    val newComponent =
        DaggerCharacterInfoComponent.builder().deps(CharacterInfoDepsProvider.deps).build()
}