package com.example.character_list.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.ViewState
import com.example.character_list.R
import com.example.character_list.databinding.FragmentCharacterListBinding
import com.example.character_list.di.CharacterListComponentViewModel
import com.example.character_list.domain.Character
import com.example.core.Constance.CHARACTER_ID
import com.example.core.base.BaseFragment
import com.example.core.base.BaseViewModelFactory
import dagger.Lazy
import javax.inject.Inject

class CharacterListFragment : BaseFragment<FragmentCharacterListBinding>() {
    @Inject
    internal lateinit var viewModelFactory: Lazy<BaseViewModelFactory<CharacterListViewModel>>
    private val viewModel: CharacterListViewModel by viewModels {
        viewModelFactory.get()
    }

    private val characterListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharactersAdapter()
    }

    private val skeletonAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SkeletonAdapter()
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCharacterListBinding =
        FragmentCharacterListBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<CharacterListComponentViewModel>()
            .newComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observeViewState()
        attachAdapterClick(findNavController())
        addLoadStateListener()
    }

    private fun initUi() {
        with(binding) {

            recyclerViewSkeleton.run {
                layoutManager = LinearLayoutManager(context)
                adapter = skeletonAdapter
            }

            recyclerView.run {
                layoutManager = LinearLayoutManager(context)
                adapter = characterListAdapter.withLoadStateHeaderAndFooter(
                    header = LoaderStateAdapter(characterListAdapter),
                    footer = LoaderStateAdapter(characterListAdapter)
                )
            }
        }
    }

    private fun addLoadStateListener() {
        characterListAdapter.addLoadStateListener { state: CombinedLoadStates ->
            when (state.refresh) {
                is LoadState.Loading -> {
                    binding.recyclerViewSkeleton.visibility = View.VISIBLE
                }
                is LoadState.NotLoading -> {
                    binding.recyclerView.isVisible
                    binding.recyclerViewSkeleton.visibility = View.GONE
                }
                is LoadState.Error -> {
                    findNavController().navigate(R.id.action_characterListFragment_to_errorFragment)
                    binding.recyclerViewSkeleton.visibility = View.GONE
                }
            }
        }
    }

    private fun attachAdapterClick(navController: NavController) {
        characterListAdapter.onAttachClick(AdapterClicks(navController))
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            bindViewState(it)
        })
    }

    private fun bindViewState(viewState: ViewState) {
        if (viewState is ViewState.Success) {
            characterListAdapter.submitData(
                lifecycle = lifecycle,
                pagingData = viewState.data as PagingData<Character>
            )
        }
    }
}

class AdapterClicks(private val findNavController: NavController) : UserListAdapterClicks {
    override fun onItemClick(model: Character?) {
        findNavController.navigate(
            R.id.action_characterListFragment_to_characterInfoFragment,
            bundleOf(CHARACTER_ID to model?.id)
        )
    }
}