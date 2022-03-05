package com.example.episodes_list.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.core.Consts.EPISODES_LIST
import com.example.core.base.BaseFragment
import com.example.episodes_list.databinding.FragmentEpisodesListBinding

class EpisodesListFragment: BaseFragment<FragmentEpisodesListBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEpisodesListBinding = FragmentEpisodesListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val episodes = arguments?.getString(EPISODES_LIST)
        Log.e("EpisodesListFragment","$episodes")
    }
}