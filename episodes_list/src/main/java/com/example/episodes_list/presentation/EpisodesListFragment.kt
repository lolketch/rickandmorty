package com.example.episodes_list.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Constance.EPISODES_LIST
import com.example.core.ViewState
import com.example.core.base.BaseFragment
import com.example.core.base.BaseViewModelFactory
import com.example.episodes_list.databinding.FragmentEpisodesListBinding
import com.example.episodes_list.di.EpisodeListComponentViewModel
import com.example.episodes_list.domain.Episode
import dagger.Lazy
import javax.inject.Inject

class EpisodesListFragment : BaseFragment<FragmentEpisodesListBinding>() {

    @Inject
    internal lateinit var viewModelFactory: Lazy<BaseViewModelFactory<EpisodesListViewModel>>
    private val viewModel: EpisodesListViewModel by viewModels {
        viewModelFactory.get()
    }

    private val episodesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        EpisodesAdapter()
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
        viewModel.fetchEpisodes(episodes!!)
        initUi()
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            bindViewState(viewState)
        })
    }

    private fun initUi() {
        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = episodesAdapter
        }
    }

    private fun bindViewState(viewState: ViewState) {
        when (viewState) {

            is ViewState.Loading -> {
                viewVisibilityLoading()
            }

            is ViewState.Success -> {
                episodesAdapter.submitList(viewState.data as List<Episode>)
                viewVisibilitySuccess()
            }

            is ViewState.Error -> {
                viewVisibilityError()
            }
        }
    }

    private fun viewVisibilitySuccess() {
        with(binding) {
            viewError.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    private fun viewVisibilityLoading() {
        with(binding) {
            viewError.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun viewVisibilityError() {
        with(binding) {
            viewError.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}