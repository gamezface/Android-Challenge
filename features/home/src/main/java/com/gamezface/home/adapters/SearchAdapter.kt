package com.gamezface.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamezface.domain.show.entity.Search
import com.gamezface.uicommon.databinding.ItemSearchResultBinding
import com.gamezface.uicommon.extensions.loadImage
import com.gamezface.uicommon.extensions.show

class SearchAdapter(
    private val onClick: (Search) -> Unit
) : ListAdapter<Search, SearchAdapter.SearchViewHolder>(TaskDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindData(getItem(position), onClick)
    }

    inner class SearchViewHolder(
        private val binding: ItemSearchResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Search, onClick: (Search) -> Unit) {
            with(binding) {
                root.setOnClickListener { onClick.invoke(item) }
                textViewTitle.text = item.getName()
                item.show?.getGenres().takeUnless { it.isNullOrEmpty() }
                    ?.run {
                        textViewGenres.show()
                        textViewGenres.text = this
                    }
                imageViewPoster.loadImage(root, item.getImage())
            }
        }
    }

    private class TaskDiffCallBack : DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem.show?.id == newItem.show?.id
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }
    }
}