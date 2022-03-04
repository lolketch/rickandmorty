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
                binding.progressBar.visibility = View.VISIBLE
                binding.imageGender.visibility = View.GONE
                binding.imageLocation.visibility = View.GONE
                binding.imageOrigin.visibility = View.GONE
            }

            is CharactersInfoViewState.Success -> {

                Log.e(
                    "Data Success", "${
                        viewState.character.episode.map {
                            it.substring(startIndex = it.lastIndexOf("/") + 1)
                        }
                    }"
                )
                binding.progressBar.visibility = View.GONE
                binding.imageGender.visibility = View.VISIBLE
                binding.imageLocation.visibility = View.VISIBLE
                binding.imageOrigin.visibility = View.VISIBLE
                with(binding) {
                    status.text = viewState.character.status
                    characterName.text = viewState.character.name
                    species.text = viewState.character.species
                    gender.text = viewState.character.gender
                    origin.text = viewState.character.origin.name
                    Glide
                        .with(this@CharacterInfoFragment)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(viewState.character.image)
                        .into(characterPhoto)
                }

            }

            is CharactersInfoViewState.Error -> {
                binding.progressBar.visibility = View.GONE
                Log.e("Data Error", "${viewState.message}")
            }
        }
    }
}