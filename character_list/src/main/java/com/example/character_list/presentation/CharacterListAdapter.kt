package com.example.character_list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.character_list.databinding.ItemBinding
import com.example.character_list.domain.Character

interface UserListAdapterClicks {
    fun onItemClick(model: Character?)
}

class CharacterListAdapter : PagingDataAdapter<Character, CharacterViewHolder>(COMPARATOR) {

    private var userListAdapterClicks: UserListAdapterClicks? = null

    fun onAttachClick(userListAdapterClicks: UserListAdapterClicks) {
        this.userListAdapterClicks = userListAdapterClicks
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            Log.e("CharacterListAdapter", "${getItem(position)?.id}")
            userListAdapterClicks?.onItemClick(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(ItemBinding.inflate(layoutInflater, parent, false))
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
                oldItem == newItem
        }
    }
}

class CharacterViewHolder(private val binding: ItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character?) {
        with(binding) {
            nameCharacter.text = character?.name
            statusCharacter.text = character?.species
            Glide
                .with(itemView)
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .load(character?.image)
                .into(imageCharacter)
        }
    }
}