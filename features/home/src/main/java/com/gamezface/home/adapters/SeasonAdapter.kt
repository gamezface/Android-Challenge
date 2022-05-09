package com.gamezface.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamezface.domain.show.entity.Episode
import com.gamezface.home.R
import com.gamezface.uicommon.databinding.ItemSeasonBinding

class SeasonAdapter(
    private val onClick: (Episode) -> Unit
) : ListAdapter<Pair<Long?, List<Episode>>, SeasonAdapter.SeasonViewHolder>(TaskDiffCallBack()) {

    private val episodeAdapter by lazy { EpisodeAdapter(onClick) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder =
        SeasonViewHolder(
            ItemSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class SeasonViewHolder(
        private val binding: ItemSeasonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Pair<Long?, List<Episode>>) {
            with(binding) {
                recyclerViewEpisodes.adapter = episodeAdapter
                episodeAdapter.submitList(item.second)
                textViewTitle.text = itemView.context?.getString(R.string.season, item.first)
            }
        }
    }

    private class TaskDiffCallBack : DiffUtil.ItemCallback<Pair<Long?, List<Episode>>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Long?, List<Episode>>,
            newItem: Pair<Long?, List<Episode>>
        ): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(
            oldItem: Pair<Long?, List<Episode>>,
            newItem: Pair<Long?, List<Episode>>
        ): Boolean {
            return oldItem == newItem
        }
    }
}