package com.example.episodes_list.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.core.base.BaseFragment
import com.example.episodes_list.databinding.FragmentEpisodesListBinding

class EpisodesListFragment: BaseFragment<FragmentEpisodesListBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEpisodesListBinding = FragmentEpisodesListBinding.inflate(inflater, container, false)
}