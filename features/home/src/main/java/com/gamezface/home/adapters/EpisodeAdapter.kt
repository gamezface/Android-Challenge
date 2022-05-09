package com.gamezface.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamezface.domain.show.entity.Episode
import com.gamezface.home.R
import com.gamezface.uicommon.databinding.ItemEpisodeBinding
import com.gamezface.uicommon.extensions.loadImage

class EpisodeAdapter(
    private val onClick: (Episode) -> Unit
) : ListAdapter<Episode, EpisodeAdapter.EpisodeViewHolder>(TaskDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(
            ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bindData(getItem(position), onClick)
    }

    inner class EpisodeViewHolder(
        private val binding: ItemEpisodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Episode, onClick: (Episode) -> Unit) {
            with(binding) {
                root.setOnClickListener { onClick.invoke(item) }
                imageViewPoster.loadImage(root, item.image?.getImageUrl())
                textViewTitle.text = item.name
                textViewEpisodeNumber.text =
                    itemView.context?.getString(R.string.episode, item.number)
            }
        }
    }

    private class TaskDiffCallBack : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }
}