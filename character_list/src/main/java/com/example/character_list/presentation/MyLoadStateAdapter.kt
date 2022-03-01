package com.example.character_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.character_list.R

class MyLoadStateAdapter : LoadStateAdapter<MyLoadStateAdapter.ItemViewHolder>() {
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
            )
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

    class ErrorViewHolder(itemView: View) : ItemViewHolder(itemView) {
        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            itemView.findViewById<TextView>(R.id.error_message).text =
                loadState.error.localizedMessage
        }
    }
}