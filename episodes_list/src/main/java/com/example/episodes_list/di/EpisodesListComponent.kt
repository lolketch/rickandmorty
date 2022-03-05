package com.example.episodes_list.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.api.RemoteDataSource
import com.example.core.di.FeatureScope
import com.example.core.di.SchedulerModule
import com.example.episodes_list.presentation.EpisodesListFragment
import dagger.Component
import kotlin.properties.Delegates

@FeatureScope
@Component(
    modules = [RepositoryModule::class, SchedulerModule::class],
    dependencies = [EpisodesListDeps::class]
)
internal interface EpisodesListComponent {

    fun inject(fragment: EpisodesListFragment)

    @Component.Builder
    interface Builder {

        fun deps(deps: EpisodesListDeps): Builder

        fun build(): EpisodesListComponent
    }
}

interface EpisodesListDeps {

    val remoteDataSource: RemoteDataSource
}

interface EpisodesListDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: EpisodesListDeps

    companion object : EpisodesListDepsProvider by EpisodeListDepsStore
}

object EpisodeListDepsStore : EpisodesListDepsProvider {
    override var deps: EpisodesListDeps by Delegates.notNull()
}

internal class EpisodeListComponentViewModel : ViewModel() {
    val newComponent =
        DaggerEpisodesListComponent.builder().deps(EpisodesListDepsProvider.deps).build()
}