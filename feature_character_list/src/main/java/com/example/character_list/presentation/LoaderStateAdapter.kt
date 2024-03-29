package com.example.character_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.character_list.R

class LoaderStateAdapter(private val adapter: CharactersAdapter) :
    LoadStateAdapter<LoaderStateAdapter.ItemViewHolder>() {
    private companion object {
        private const val ERROR = 1
        private const val PROGRESS = 0
    }

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        is LoadState.Loading -> PROGRESS
        is LoadState.Error -> ERROR
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when (loadState) {
            is LoadState.Loading -> ProgressViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_progress, parent, false)
            )
            is LoadState.Error -> ErrorViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_error, parent, false)
            ) { adapter.retry() }
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(loadState: LoadState)
    }

    class ProgressViewHolder(itemView: View) : ItemViewHolder(itemView) {
        override fun bind(loadState: LoadState) {
            // do nothing
        }
    }

    class ErrorViewHolder(itemView: View, private val retryCallback: () -> Unit) :
        ItemViewHolder(itemView) {
        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            itemView.findViewById<ImageButton>(R.id.retryBtn)
                .also {
                    it.setOnClickListener { retryCallback() }
                }
        }
    }
}