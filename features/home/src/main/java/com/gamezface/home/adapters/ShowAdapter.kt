package com.gamezface.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamezface.domain.show.entity.Show
import com.gamezface.uicommon.databinding.ItemShowPosterBinding
import com.gamezface.uicommon.extensions.loadImage

class ShowAdapter(
    private val onClick: (Show) -> Unit
) : ListAdapter<Show, ShowAdapter.ShowViewHolder>(TaskDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShowViewHolder(
        ItemShowPosterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onClick)
        }
    }

    inner class ShowViewHolder(
        private val binding: ItemShowPosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Show, onClick: (Show) -> Unit) {
            with(binding) {
                root.setOnClickListener { onClick.invoke(item) }
                textViewTitle.text = item.name
                imageViewPoster.loadImage(root, item.image?.getImageUrl())
            }
        }
    }

    private class TaskDiffCallBack : DiffUtil.ItemCallback<Show>() {
        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem == newItem
        }
    }

}