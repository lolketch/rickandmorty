package com.example.character_list.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.character_list.CharactersListViewState
import com.example.character_list.databinding.FragmentCharacterListBinding
import com.example.character_list.di.CharacterListComponentViewModel
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
        CharacterListAdapter()
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
        observeViewState()

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = characterListAdapter.withLoadStateHeaderAndFooter(
                header = MyLoadStateAdapter(),
                footer = MyLoadStateAdapter()
            )
        }
        characterListAdapter.addLoadStateListener { state ->
            if (state.refresh == LoadState.Loading) {
            }
            else {
                binding.recyclerView.isVisible
            }
        }
    }

    private fun observeViewState() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            bindViewState(it)
        })
    }

    private fun bindViewState(viewState: CharactersListViewState) {
        when (viewState) {

            is CharactersListViewState.Loading -> {
                Log.e("Data Loading","Start")
            }

            is CharactersListViewState.Success -> {
                characterListAdapter.submitData(lifecycle = lifecycle, pagingData = viewState.pagingData)
                Log.e("Data Success","${viewState.pagingData}")
            }

            is CharactersListViewState.Error -> {
                Log.e("Data Error","${viewState.message}")
            }
        }
    }
}