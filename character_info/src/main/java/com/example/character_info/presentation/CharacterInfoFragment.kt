package com.example.character_info.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.character_info.CharactersInfoViewState
import com.example.character_info.databinding.FragmentCharacterInfoBinding
import com.example.character_info.di.CharacterInfoComponentViewModel
import com.example.core.Consts.CHARACTER_ID
import com.example.core.base.BaseFragment
import com.example.core.base.BaseViewModelFactory
import dagger.Lazy
import javax.inject.Inject

class CharacterInfoFragment : BaseFragment<FragmentCharacterInfoBinding>() {

    @Inject
    internal lateinit var viewModelFactory: Lazy<BaseViewModelFactory<CharacterInfoViewModel>>
    private val viewModel: CharacterInfoViewModel by viewModels {
        viewModelFactory.get()
    }
    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<CharacterInfoComponentViewModel>()
            .newComponent.inject(this)
        super.onAttach(context)
    }
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCharacterInfoBinding =
        FragmentCharacterInfoBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterId: Int = arguments?.getInt(CHARACTER_ID)!!
        viewModel.fetchCharacterInfo(characterId)

        viewModel.viewState.observe(viewLifecycleOwner, {
            bindViewState(it)
        })
    }

    private fun bindViewState(viewState: CharactersInfoViewState) {
        when (viewState) {

            is CharactersInfoViewState.Loading -> {
                Log.e("Data Loading", "Start")
            }

            is CharactersInfoViewState.Success -> {

                Log.e("Data Success", "${viewState.character}")
            }

            is CharactersInfoViewState.Error -> {
                Log.e("Data Error", "${viewState.message}")
            }
        }
    }
}