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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.character_info.CharactersInfoViewState
import com.example.character_info.databinding.FragmentCharacterInfoBinding
import com.example.character_info.di.CharacterInfoComponentViewModel
import com.example.character_info.domain.Character
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
        observe()
        binding.btnEpisodes.setOnClickListener {

        }
    }

    private fun observe() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            bindViewState(viewState)
        })
    }

    private fun bindViewState(viewState: CharactersInfoViewState) {
        when (viewState) {

            is CharactersInfoViewState.Loading -> {
                viewVisibilityLoading()
            }

            is CharactersInfoViewState.Success -> {
                viewVisibilitySuccess()
                initViewSuccess(viewState.character)
            }

            is CharactersInfoViewState.Error -> {
                viewVisibilityError()
            }
        }
    }

    private fun initViewSuccess(character: Character) {
        with(binding) {
            status.text = character.status
            characterName.text = character.name
            species.text = character.species
            gender.text = character.gender
            origin.text = character.origin.name
            location.text = character.location.name
            Glide
                .with(this@CharacterInfoFragment)
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(character.image)
                .into(characterPhoto)
        }
    }

    private fun viewVisibilitySuccess() {
        with(binding) {
            progressBar.visibility = View.GONE
            imageGender.visibility = View.VISIBLE
            imageLocation.visibility = View.VISIBLE
            imageOrigin.visibility = View.VISIBLE
        }
    }

    private fun viewVisibilityLoading() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            imageGender.visibility = View.GONE
            imageLocation.visibility = View.GONE
            imageOrigin.visibility = View.GONE
        }
    }

    private fun viewVisibilityError() {
        with(binding) {
            progressBar.visibility = View.GONE
        }
    }
}