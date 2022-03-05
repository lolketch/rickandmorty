package com.example.episodes_list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.episodes_list.databinding.ItemEpisodeBinding
import com.example.episodes_list.domain.Episode

class EpisodesAdapter : ListAdapter<Episode, CharacterViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(ItemEpisodeBinding.inflate(layoutInflater, parent, false))
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean =
                oldItem == newItem
        }
    }
}

class CharacterViewHolder(private val binding: ItemEpisodeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(episode: Episode?) {
        with(binding) {
            nameEpisode.text = episode?.name
            codeEpisode.text = episode?.episode
            airDate.text = episode?.air_date
        }
    }
}