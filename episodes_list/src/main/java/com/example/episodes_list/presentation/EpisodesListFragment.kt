package com.example.episodes_list.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.core.Consts.EPISODES_LIST
import com.example.core.base.BaseFragment
import com.example.core.base.BaseViewModelFactory
import com.example.episodes_list.EpisodesInfoViewState
import com.example.episodes_list.databinding.FragmentEpisodesListBinding
import com.example.episodes_list.di.EpisodeListComponentViewModel
import dagger.Lazy
import javax.inject.Inject

class EpisodesListFragment : BaseFragment<FragmentEpisodesListBinding>() {

    @Inject
    internal lateinit var viewModelFactory: Lazy<BaseViewModelFactory<EpisodesListViewModel>>
    private val viewModel: EpisodesListViewModel by viewModels {
        viewModelFactory.get()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEpisodesListBinding = FragmentEpisodesListBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<EpisodeListComponentViewModel>()
            .newComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val episodes = arguments?.getString(EPISODES_LIST)
        Log.e("EpisodesListFragment", "$episodes")
        viewModel.fetchEpisodes(episodes!!)
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            bindViewState(viewState)
        })
    }

    private fun bindViewState(viewState: EpisodesInfoViewState) {
        when (viewState) {

            is EpisodesInfoViewState.Loading -> {
                Log.e("EpisodesInfoViewState", "Loading")
            }

            is EpisodesInfoViewState.Success -> {
                Log.e("EpisodesInfoViewState", "Success ${viewState.episodes}")
            }

            is EpisodesInfoViewState.Error -> {
                Log.e("EpisodesInfoViewState", "Error ${viewState.message}")
            }
        }
    }
}