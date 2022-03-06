package com.example.episodes_list.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.Constance.EPISODES_LIST
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

    private val episodesAdapter by lazy(LazyThreadSafetyMode.NONE) {
        EpisodesAdapter()
    }

    private val skeletonAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SkeletonAdapter()
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
//        binding.recyclerViewSkeleton.run {
//            layoutManager = LinearLayoutManager(context)
//            adapter = skeletonAdapter
//        }

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = episodesAdapter
        }
    }

    private fun bindViewState(viewState: EpisodesInfoViewState) {
        when (viewState) {

            is EpisodesInfoViewState.Loading -> {
                Log.e("EpisodesInfoViewState", "Loading")
                viewVisibilityLoading()
            }

            is EpisodesInfoViewState.Success -> {
                episodesAdapter.submitList(viewState.episodes)
                viewVisibilitySuccess()
                Log.e("EpisodesInfoViewState", "Success ${viewState.episodes}")
            }

            is EpisodesInfoViewState.Error -> {
                viewVisibilityError()
                Log.e("EpisodesInfoViewState", "Error ${viewState.message}")
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